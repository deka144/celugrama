package com.ponc.config;
// junto a OpenApiConfig y ApiResponseAdvice

import com.ponc.dto.ApiResponse; // tu envoltorio { data, success, errorMessage }
import io.swagger.v3.core.converter.AnnotatedType; // describe un tipo Java para que swagger-core arme su esquema
import io.swagger.v3.core.converter.ModelConverters; // convierte un tipo Java en esquema de Swagger
import io.swagger.v3.core.converter.ResolvedSchema; // resultado: el esquema y los modelos que usa
import io.swagger.v3.oas.models.Components; // lista de modelos (esquemas) que se ven al final de Swagger
import io.swagger.v3.oas.models.OpenAPI; // el documento completo de Swagger
import io.swagger.v3.oas.models.Operation; // un endpoint (GET, POST, etc.) dentro del documento
import io.swagger.v3.oas.models.media.BooleanSchema; // esquema de un campo true/false
import io.swagger.v3.oas.models.media.Content; // contenido de una respuesta (por tipo de medio)
import io.swagger.v3.oas.models.media.MediaType; // un tipo de medio de la respuesta (aqui, */*)
import io.swagger.v3.oas.models.media.ObjectSchema; // esquema de un objeto con campos
import io.swagger.v3.oas.models.media.Schema; // esquema generico
import io.swagger.v3.oas.models.media.StringSchema; // esquema de un campo de texto
import org.springdoc.core.customizers.OpenApiCustomizer; // permite ajustar el documento completo al final
import org.springdoc.core.customizers.OperationCustomizer; // permite ajustar cada endpoint mientras se arma
import org.springframework.http.ResponseEntity; // tipo que devuelven tus controllers
import org.springframework.stereotype.Component; // para que Spring registre esta clase solo
import org.springframework.web.method.HandlerMethod; // el metodo del controller que se esta documentando

import java.lang.reflect.ParameterizedType; // tipo con genericos, como ResponseEntity<Long>
import java.lang.reflect.Type; // cualquier tipo de Java
import java.lang.reflect.WildcardType; // el tipo comodin ResponseEntity<?>
import java.util.LinkedHashMap; // mapa que conserva el orden de los campos del ejemplo
import java.util.Map; // mapa clave-valor
import java.util.concurrent.ConcurrentHashMap; // mapa seguro para guardar los modelos que se van generando

// Hace que Swagger dibuje el envoltorio ApiResponse que tu ApiResponseAdvice
// le pone a todas las respuestas. Solo cambia lo que Swagger muestra: la API
// devuelve exactamente lo mismo que antes.
@Component // Spring la registra sola, y springdoc la usa porque implementa sus 2 interfaces
public class ApiResponseEnvelopeCustomizer implements OperationCustomizer, OpenApiCustomizer { // dos tareas: ajustar cada endpoint y ajustar el documento final

    private static final String ERROR_SCHEMA = "ApiResponseError"; // nombre del modelo que se usa en las respuestas de error

    // Modelos del envoltorio que se van generando (ApiResponseLong, ApiResponseListCellDTO, etc.)
    private final Map<String, Schema> envelopeSchemas = new ConcurrentHashMap<>(); // se llenan en customize() y se publican en customise()

    // Se ejecuta una vez por cada endpoint mientras springdoc arma el documento.
    @Override // implementa el metodo de OperationCustomizer
    public Operation customize(Operation operation, HandlerMethod handlerMethod) { // recibe el endpoint y el metodo Java que lo implementa
        if (operation.getResponses() == null) { // si el endpoint no tiene respuestas documentadas
            return operation; // no hay nada que ajustar
        } // cierra el if

        // Tipo del dato que devuelve el metodo, sin el ResponseEntity (ej. Long, List<CellDTO>)
        Type bodyType = bodyTypeOf(handlerMethod.getMethod().getGenericReturnType()); // null si no devuelve contenido (Void)
        Schema<?> envelopeRef = null; // referencia al modelo del envoltorio para este endpoint
        if (bodyType != null) { // solo si el endpoint devuelve algo
            ResolvedSchema resolved = ModelConverters.getInstance() // pide a swagger-core el esquema de ApiResponse<tipo>
                    .resolveAsResolvedSchema(new AnnotatedType(envelopeOf(bodyType)).resolveAsRef(true)); // resolveAsRef(true): devuelve una referencia al modelo
            if (resolved != null && resolved.schema != null) { // si se pudo armar el esquema
                if (resolved.referencedSchemas != null) { // y trae modelos asociados
                    envelopeSchemas.putAll(resolved.referencedSchemas); // los guarda para publicarlos al final
                } // cierra el if de los modelos
                envelopeRef = resolved.schema; // la referencia que usaran las respuestas exitosas
            } // cierra el if del esquema
        } // cierra el if del tipo

        for (Map.Entry<String, io.swagger.v3.oas.models.responses.ApiResponse> entry : operation.getResponses().entrySet()) { // recorre cada respuesta (200, 404, 500...)
            String code = entry.getKey(); // el codigo, como texto
            io.swagger.v3.oas.models.responses.ApiResponse response = entry.getValue(); // la respuesta documentada
            if (code.startsWith("2") && !code.equals("204") && envelopeRef != null) { // respuestas exitosas con contenido
                wrapSuccess(response, envelopeRef); // el contenido pasa a ser el envoltorio
            } else if (code.startsWith("4") || code.startsWith("5")) { // respuestas de error (400, 404, 500...)
                addErrorExample(response, code); // les agrega el ejemplo del envoltorio de error
            } // cierra el if/else
        } // cierra el for
        return operation; // devuelve el endpoint ya ajustado
    } // cierra customize

    // Se ejecuta una sola vez, al final, cuando el documento ya esta completo.
    @Override // implementa el metodo de OpenApiCustomizer
    public void customise(OpenAPI openApi) { // recibe el documento completo
        if (openApi.getComponents() == null) { // si todavia no hay lista de modelos
            openApi.setComponents(new Components()); // la crea
        } // cierra el if
        Components components = openApi.getComponents(); // la lista de modelos del documento
        envelopeSchemas.forEach((name, schema) -> { // por cada modelo del envoltorio que se genero
            if (components.getSchemas() == null || !components.getSchemas().containsKey(name)) { // si todavia no esta en la lista
                components.addSchemas(name, schema); // lo agrega para que aparezca en la seccion Schemas
            } // cierra el if
        }); // cierra el forEach
        components.addSchemas(ERROR_SCHEMA, errorSchema()); // agrega el modelo de las respuestas de error
    } // cierra customise

    // Cambia el contenido de una respuesta exitosa para que apunte al modelo del envoltorio.
    private void wrapSuccess(io.swagger.v3.oas.models.responses.ApiResponse response, Schema<?> envelopeRef) { // la respuesta y el modelo a usar
        Content content = response.getContent(); // el contenido que ya tenia
        if (content == null || content.isEmpty()) { // si no tenia contenido (ej. un 201 documentado solo con texto)
            content = new Content(); // crea uno nuevo
            content.addMediaType("*/*", new MediaType()); // con el tipo de medio que ya muestra tu Swagger
            response.setContent(content); // lo asigna a la respuesta
        } // cierra el if
        for (MediaType mediaType : content.values()) { // por cada tipo de medio de la respuesta
            mediaType.setSchema(envelopeRef); // el esquema pasa a ser el envoltorio (con el dato adentro de data)
        } // cierra el for
    } // cierra wrapSuccess

    // Agrega a una respuesta de error el ejemplo { data: null, success: false, errorMessage: ... }.
    // Reemplaza el contenido que springdoc ya le puso (el ejemplo del exito), para que el error no se vea como un exito.
    private void addErrorExample(io.swagger.v3.oas.models.responses.ApiResponse response, String code) { // la respuesta y su codigo
        MediaType mediaType = new MediaType(); // el tipo de medio de la respuesta de error
        mediaType.setSchema(new Schema<>().$ref("#/components/schemas/" + ERROR_SCHEMA)); // apunta al modelo de error
        Map<String, Object> example = new LinkedHashMap<>(); // el ejemplo, con los campos en orden
        example.put("data", null); // en un error, data va vacio
        example.put("success", false); // success en false
        example.put("errorMessage", exampleMessage(code, response.getDescription())); // el mensaje del error
        mediaType.setExample(example); // asigna el ejemplo al tipo de medio
        Content content = new Content(); // contenido nuevo de la respuesta
        content.addMediaType("*/*", mediaType); // con el mismo tipo de medio */*
        response.setContent(content); // lo asigna a la respuesta
    } // cierra addErrorExample

    // Mensaje de ejemplo para una respuesta de error, a partir de su descripcion.
    private String exampleMessage(String code, String description) { // codigo y descripcion de la respuesta
        if (code.equals("404")) { // el id no existe
            return "ID NOT FOUND :1"; // asi responde tu ModelNotFoundException
        } // cierra el if
        if (description == null) { // si no hay descripcion
            return "Error"; // texto generico
        } // cierra el if
        int start = description.indexOf('«'); // busca el primer mensaje entre « »
        int end = description.indexOf('»'); // y donde termina
        if (start >= 0 && end > start) { // si hay un mensaje entre « »
            return description.substring(start + 1, end); // usa ese mensaje como ejemplo
        } // cierra el if
        return description; // si no, usa la descripcion completa
    } // cierra exampleMessage

    // Modelo de las respuestas de error: success = false y el mensaje en errorMessage.
    private Schema<?> errorSchema() { // arma el modelo a mano, porque no depende de ningun tipo de dato
        return new ObjectSchema() // un objeto con 3 campos
                .description("Respuesta de error de la API") // titulo del modelo en Swagger
                .addProperty("data", new Schema<>().nullable(true).description("Siempre null cuando hay un error")) // data: vacio
                .addProperty("success", new BooleanSchema().example(false)) // success: false
                .addProperty("errorMessage", new StringSchema().description("Mensaje del error")); // errorMessage: el texto
    } // cierra errorSchema

    // De ResponseEntity<T> saca T, el dato que realmente se devuelve.
    private static Type bodyTypeOf(Type returnType) { // el tipo que devuelve el metodo del controller
        Type body = returnType; // por defecto, el mismo tipo
        if (returnType instanceof ParameterizedType parameterized && parameterized.getRawType() == ResponseEntity.class) { // si es ResponseEntity<algo>
            body = parameterized.getActualTypeArguments()[0]; // se queda con el "algo"
        } // cierra el if
        if (body == Void.class || body == void.class || body instanceof WildcardType) { // sin contenido, o tipo comodin
            return null; // no hay dato que envolver
        } // cierra el if
        return body; // el tipo del dato
    } // cierra bodyTypeOf

    // Arma el tipo ApiResponse<T> a partir de T, para que swagger-core lo convierta en modelo.
    private static Type envelopeOf(Type inner) { // el tipo T
        return new ParameterizedType() { // un tipo con genericos hecho a mano
            @Override // devuelve los tipos entre < >
            public Type[] getActualTypeArguments() { return new Type[]{inner}; } // el unico: T
            @Override // devuelve la clase base
            public Type getRawType() { return ApiResponse.class; } // ApiResponse
            @Override // devuelve la clase que lo contiene
            public Type getOwnerType() { return null; } // ninguna
        }; // cierra el tipo
    } // cierra envelopeOf
} // cierra la clase

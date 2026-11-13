package com.ponc.controller;

import com.ponc.dto.GuestResponseDTO;
import com.ponc.dto.GuestRequestDTO;
import com.ponc.dto.PageResponseDTO;
import com.ponc.model.Guest;
import com.ponc.service.IGuestService;
import com.ponc.util.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( // agrupa en Swagger todos los endpoints de este controller
        name = "Invitados", // nombre del grupo que se ve en Swagger
        description = "Asistencia de invitados a las células") // explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/Guests")
@AllArgsConstructor
public class GuestController {
    private final IGuestService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los invitados", // título corto que se ve en la lista de endpoints
            description = "Devuelve los invitados registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de invitados (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los invitados.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<GuestResponseDTO>> findAll()throws Exception{
        List<GuestResponseDTO> list =mapperUtil.mapList(service.findAll(), GuestResponseDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un invitado por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el invitado que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del invitado", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El invitado encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el invitado con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Invitado.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<GuestResponseDTO> findById(@PathVariable("id") Integer id)throws Exception{
        GuestResponseDTO objDTO=mapperUtil.map(service.findById(id), GuestResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un invitado nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un invitado nuevo con los datos que se envían en el cuerpo (JSON). " +
                    "Solo se puede registrar el día en que se realiza la célula y dentro de las 4 horas " +
                    "siguientes a su hora de inicio. Una persona solo puede registrarse una vez como invitado " +
                    "por periodo y no puede ser miembro de una célula. Si completa 5 semanas seguidas como invitado " +
                    "en la misma célula, pasa a ser Discípulo automáticamente.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El invitado se creó correctamente"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno. " +
                    "El mensaje viene en errorMessage, por ejemplo: «La célula indicada no existe.»," +
                    " «Solo se puede registrar el día que se realiza la célula (<día>).», " +
                    "«Todavía no es la hora de la célula (empieza a las <hora>).», " +
                    "«Ya pasó la ventana de 4 horas para registrar esta célula.», " +
                    "«Esta persona ya registró asistencia como invitado en este periodo.», " +
                    "«Esta persona ya es miembro de una célula, no puede registrarse como invitado.», " +
                    "«Error al crear el Invitado».") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<GuestResponseDTO> save (@Valid @RequestBody GuestRequestDTO dto)throws Exception{
        Guest obj=service.save(mapperUtil.map(dto, Guest.class));
        GuestResponseDTO objDTO=mapperUtil.map(obj, GuestResponseDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un invitado existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del invitado que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del invitado", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El invitado se actualizó correctamente"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el invitado con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el invitado.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<GuestResponseDTO> update (@PathVariable("id") Integer id, @Valid @RequestBody GuestRequestDTO dto)throws Exception{
        // el @Valid ya no va en el id (no tiene reglas que revisar); ahora va en el cuerpo, que es lo que se valida
        Guest obj=service.update(mapperUtil.map(dto, Guest.class),id);
        GuestResponseDTO objDTO=mapperUtil.map(obj, GuestResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un invitado", // título corto que se ve en la lista de endpoints
            description = "Elimina el invitado que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del invitado", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El invitado se eliminó (no devuelve contenido)"),
            // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el invitado con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Invitado.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation( // PAGINADO: documenta este endpoint en Swagger
            summary = "Lista los invitados por páginas, con búsqueda opcional", // PAGINADO: título corto que se ve en la lista de endpoints
            description = "Devuelve una página de invitados. Sin search trae todos; con search filtra por nombre o apellidos de la persona.", // PAGINADO: explicación que se ve al abrir el endpoint
            parameters = { // PAGINADO: explica los parámetros del endpoint
                    @Parameter(name = "search", in = ParameterIn.QUERY, description = "Texto a buscar (opcional)", example = "juan"), // PAGINADO: texto de búsqueda; va en la URL (QUERY)
                    @Parameter(name = "page", in = ParameterIn.QUERY, description = "Número de página; la primera es la 0", example = "0"), // PAGINADO: página pedida
                    @Parameter(name = "size", in = ParameterIn.QUERY, description = "Registros por página (máximo 100)", example = "10") // PAGINADO: tamaño de la página
            }) // PAGINADO: cierra la lista de parámetros y el @Operation
    @ApiResponses({ // PAGINADO: respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La página pedida (content vacío si no hay coincidencias)"), // PAGINADO: código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Invitados.") // PAGINADO: código 500: error interno
    }) // PAGINADO: cierra la lista de respuestas
    @GetMapping("/page") // PAGINADO: GET /Guests/page?search=&page=0&size=10; no choca con /{id} porque "page" no es un número
    public ResponseEntity<PageResponseDTO<GuestResponseDTO>> findPage( // PAGINADO: devuelve la página dentro de PageResponseDTO
                                                                       @RequestParam(name = "search", required = false) String search, // PAGINADO: texto a buscar; si no se envía, no filtra
                                                                       @RequestParam(name = "page", defaultValue = "0") int page, // PAGINADO: número de página; por defecto la primera (0)
                                                                       @RequestParam(name = "size", defaultValue = "10") int size) throws Exception { // PAGINADO: registros por página; por defecto 10
        Page<GuestResponseDTO> result = service.findPage(search, page, size) // PAGINADO: pide la página de entidades al service
                .map(e -> mapperUtil.map(e, GuestResponseDTO.class)); // PAGINADO: convierte cada entidad a su DTO de salida
        return ResponseEntity.ok(PageResponseDTO.from(result)); // PAGINADO: responde 200 con la página; ApiResponseAdvice la envuelve en data
    }

}

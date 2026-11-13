package com.ponc.controller;

import com.ponc.dto.PageResponseDTO;
import com.ponc.dto.SpiritualGrowthPersonResponseDTO;
import com.ponc.dto.SpiritualGrowthPersonRequestDTO;
import com.ponc.model.SpiritualGrowthPerson;
import com.ponc.service.ISpiritualGrowthPersonService;
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
        name = "Crecimiento espiritual de personas", // nombre del grupo que se ve en Swagger
        description = "Etapa de crecimiento espiritual en la que está cada persona")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/spiritualgrowthpersons")
@AllArgsConstructor
public class SpiritualGrowthPersonController {

    private final ISpiritualGrowthPersonService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los crecimientos espirituales de personas", // título corto que se ve en la lista de endpoints
            description = "Devuelve los crecimientos espirituales de personas registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de crecimientos espirituales de personas (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Crecimientos Espirituales de las Personas.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<SpiritualGrowthPersonResponseDTO>> findAll() throws Exception{
        List<SpiritualGrowthPersonResponseDTO> list = mapperUtil.mapList(service.findAll(), SpiritualGrowthPersonResponseDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un crecimiento espiritual de persona por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el crecimiento espiritual de persona que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del crecimiento espiritual de la persona", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El crecimiento espiritual de persona encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el crecimiento espiritual de persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Crecimiento Espiritual de la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<SpiritualGrowthPersonResponseDTO> findById(@PathVariable("id") Integer id) throws Exception{
        SpiritualGrowthPersonResponseDTO objDTO=mapperUtil.map(service.findById(id), SpiritualGrowthPersonResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un crecimiento espiritual de persona nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un crecimiento espiritual de persona nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El crecimiento espiritual de persona se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear el Crecimiento Espiritual de la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<SpiritualGrowthPersonResponseDTO> create(@Valid @RequestBody SpiritualGrowthPersonRequestDTO dto) throws Exception{
        SpiritualGrowthPerson obj=service.save(mapperUtil.map(dto,SpiritualGrowthPerson.class));
        SpiritualGrowthPersonResponseDTO objDTO=mapperUtil.map(obj, SpiritualGrowthPersonResponseDTO.class);

        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un crecimiento espiritual de persona existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del crecimiento espiritual de persona que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del crecimiento espiritual de la persona", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El crecimiento espiritual de persona se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el crecimiento espiritual de persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Crecimiento Espiritual de la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<SpiritualGrowthPersonResponseDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody SpiritualGrowthPersonRequestDTO dto) throws Exception{
        // el @Valid ya no va en el id (no tiene reglas que revisar); ahora va en el cuerpo, que es lo que se valida
        SpiritualGrowthPerson obj=service.update(mapperUtil.map(dto,SpiritualGrowthPerson.class),id);
        SpiritualGrowthPersonResponseDTO objDTO=mapperUtil.map(obj, SpiritualGrowthPersonResponseDTO.class);

        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un crecimiento espiritual de persona", // título corto que se ve en la lista de endpoints
            description = "Elimina el crecimiento espiritual de persona que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del crecimiento espiritual de la persona", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El crecimiento espiritual de persona se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el crecimiento espiritual de persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Crecimiento Espiritual de la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation( // PAGINADO: documenta este endpoint en Swagger
            summary = "Lista los crecimientos espirituales de personas por páginas, con búsqueda opcional", // PAGINADO: título corto que se ve en la lista de endpoints
            description = "Devuelve una página de crecimientos espirituales de personas. Sin search trae todos; con search filtra por nombre o apellidos de la persona, o descripción del crecimiento.", // PAGINADO: explicación que se ve al abrir el endpoint
            parameters = { // PAGINADO: explica los parámetros del endpoint
                    @Parameter(name = "search", in = ParameterIn.QUERY, description = "Texto a buscar (opcional)", example = "juan"), // PAGINADO: texto de búsqueda; va en la URL (QUERY)
                    @Parameter(name = "page", in = ParameterIn.QUERY, description = "Número de página; la primera es la 0", example = "0"), // PAGINADO: página pedida
                    @Parameter(name = "size", in = ParameterIn.QUERY, description = "Registros por página (máximo 100)", example = "10") // PAGINADO: tamaño de la página
            }) // PAGINADO: cierra la lista de parámetros y el @Operation
    @ApiResponses({ // PAGINADO: respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La página pedida (content vacío si no hay coincidencias)"), // PAGINADO: código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Crecimientos espirituales de las personas.") // PAGINADO: código 500: error interno
    }) // PAGINADO: cierra la lista de respuestas
    @GetMapping("/page") // PAGINADO: GET /spiritualgrowthpersons/page?search=&page=0&size=10; no choca con /{id} porque "page" no es un número
    public ResponseEntity<PageResponseDTO<SpiritualGrowthPersonResponseDTO>> findPage( // PAGINADO: devuelve la página dentro de PageResponseDTO
                                                                                       @RequestParam(name = "search", required = false) String search, // PAGINADO: texto a buscar; si no se envía, no filtra
                                                                                       @RequestParam(name = "page", defaultValue = "0") int page, // PAGINADO: número de página; por defecto la primera (0)
                                                                                       @RequestParam(name = "size", defaultValue = "10") int size) throws Exception { // PAGINADO: registros por página; por defecto 10
        Page<SpiritualGrowthPersonResponseDTO> result = service.findPage(search, page, size) // PAGINADO: pide la página de entidades al service
                .map(e -> mapperUtil.map(e, SpiritualGrowthPersonResponseDTO.class)); // PAGINADO: convierte cada entidad a su DTO de salida
        return ResponseEntity.ok(PageResponseDTO.from(result)); // PAGINADO: responde 200 con la página; ApiResponseAdvice la envuelve en data
    }


}

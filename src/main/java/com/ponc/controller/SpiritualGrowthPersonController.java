package com.ponc.controller;

import com.ponc.dto.SpiritualGrowthPersonDTO;
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
    public ResponseEntity<List<SpiritualGrowthPersonDTO>> findAll() throws Exception{
        List<SpiritualGrowthPersonDTO> list = mapperUtil.mapList(service.findAll(), SpiritualGrowthPersonDTO.class);
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
    public ResponseEntity<SpiritualGrowthPersonDTO> findById(@PathVariable("id") Integer id) throws Exception{
        SpiritualGrowthPersonDTO objDTO=mapperUtil.map(service.findById(id),SpiritualGrowthPersonDTO.class);
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
    public ResponseEntity<SpiritualGrowthPersonDTO> create(@Valid @RequestBody SpiritualGrowthPersonDTO dto) throws Exception{
        SpiritualGrowthPerson obj=service.save(mapperUtil.map(dto,SpiritualGrowthPerson.class));
        SpiritualGrowthPersonDTO objDTO=mapperUtil.map(obj,SpiritualGrowthPersonDTO.class);

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
    public ResponseEntity<SpiritualGrowthPersonDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody SpiritualGrowthPersonDTO dto) throws Exception{

        SpiritualGrowthPerson obj=service.update(mapperUtil.map(dto,SpiritualGrowthPerson.class),id);
        SpiritualGrowthPersonDTO objDTO=mapperUtil.map(obj,SpiritualGrowthPersonDTO.class);

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


}

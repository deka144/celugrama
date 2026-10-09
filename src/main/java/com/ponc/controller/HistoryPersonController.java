package com.ponc.controller;

import com.ponc.dto.HistoryPersonDTO;
import com.ponc.model.HistoryPerson;
import com.ponc.service.IHistoryPersonService;
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
        name = "Historial de personas", // nombre del grupo que se ve en Swagger
        description = "Registro de los movimientos importantes de cada persona")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/historypersons")
@AllArgsConstructor
public class HistoryPersonController {
    private final IHistoryPersonService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los historiales de personas", // título corto que se ve en la lista de endpoints
            description = "Devuelve los historiales de personas registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de historiales de personas (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Historiales de las personas.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<HistoryPersonDTO>> findAll()throws Exception{
        List<HistoryPersonDTO> list=mapperUtil.mapList(service.findAll(),HistoryPersonDTO.class);
        return ResponseEntity.ok(list);
    }


    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un historial de persona por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el historial de persona que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del historial", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El historial de persona encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el historial de persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Historial de la persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<HistoryPersonDTO> findById(@PathVariable("id") Integer id )throws Exception{
        HistoryPersonDTO objDTO=mapperUtil.map(service.findById(id),HistoryPersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un historial de persona nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un historial de persona nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El historial de persona se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear el Historial de la persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<HistoryPersonDTO> save (@Valid @RequestBody HistoryPersonDTO dto)throws Exception{
        HistoryPerson obj=service.save(mapperUtil.map(dto,HistoryPerson.class));
        HistoryPersonDTO objDTO=mapperUtil.map(obj, HistoryPersonDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un historial de persona existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del historial de persona que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del historial", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El historial de persona se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el historial de persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Historial de la persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<HistoryPersonDTO> update (@Valid @PathVariable("id") Integer id,@RequestBody HistoryPersonDTO dto )throws Exception{
        HistoryPerson obj=service.update(mapperUtil.map(dto,HistoryPerson.class),id);
        HistoryPersonDTO objDTO=mapperUtil.map(obj,HistoryPersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un historial de persona", // título corto que se ve en la lista de endpoints
            description = "Elimina el historial de persona que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del historial", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El historial de persona se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el historial de persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Historial de la persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

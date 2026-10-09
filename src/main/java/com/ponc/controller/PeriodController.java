package com.ponc.controller;

import com.ponc.dto.PeriodDTO;
import com.ponc.model.Period;
import com.ponc.service.IPeriodService;
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
        name = "Periodos", // nombre del grupo que se ve en Swagger
        description = "Semanas en las que se registra la asistencia")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/periods")
@AllArgsConstructor
public class PeriodController {
    private final IPeriodService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los periodos", // título corto que se ve en la lista de endpoints
            description = "Devuelve los periodos registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de periodos (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<PeriodDTO>> findAll()throws Exception{
        List<PeriodDTO> list=mapperUtil.mapList(service.findAll(),PeriodDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un periodo por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el periodo que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del periodo", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El periodo encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el periodo con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<PeriodDTO> finById(@PathVariable("id") Integer id)throws Exception{
        PeriodDTO objDTO=mapperUtil.map(service.findById(id),PeriodDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un periodo nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un periodo nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El periodo se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<PeriodDTO> save(@Valid @RequestBody PeriodDTO dto)throws Exception{
        Period obj=service.save(mapperUtil.map(dto,Period.class));
        PeriodDTO objDTO=mapperUtil.map(obj,PeriodDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un periodo existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del periodo que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del periodo", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El periodo se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el periodo con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<PeriodDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody PeriodDTO dto)throws Exception{
        Period obj = service.update(mapperUtil.map(dto,Period.class),id);
        PeriodDTO objDTO=mapperUtil.map(obj,PeriodDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un periodo", // título corto que se ve en la lista de endpoints
            description = "Elimina el periodo que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del periodo", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El periodo se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el periodo con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // Endpoint manual: aparece en Swagger como POST /periods/generar-mes-actual
    // Lo ejecutas cuando quieras desde ahi, sin esperar al dia 1
    @Operation( // documenta este endpoint en Swagger
            summary = "Genera los periodos del mes actual", // título corto que se ve en la lista de endpoints
            description = "Crea las semanas (periodos) del mes actual, de lunes a domingo, sin esperar al proceso automático del día 1. Si los periodos de ese mes ya existen, no crea nada y devuelve una lista vacía.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Periodos creados (lista vacía si el mes ya tenía periodos)") // código 200: todo salió bien
    }) // cierra la lista de respuestas
    @PostMapping("/generar-periodos-xmes")
    public ResponseEntity<List<PeriodDTO>> generarMesActual() throws Exception {
        List<PeriodDTO> list = mapperUtil.mapList(service.generarPeriodosDelMesActual(), PeriodDTO.class);
        return ResponseEntity.ok(list);
    }


}

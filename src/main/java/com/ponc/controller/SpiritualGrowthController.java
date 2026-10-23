package com.ponc.controller;

import com.ponc.dto.OnUpdate;
import com.ponc.dto.SpiritualGrowthDTO;
import com.ponc.dto.SpiritualGrowthPersonDTO;
import com.ponc.dto.StateDTO;
import com.ponc.model.SpiritualGrowth;
import com.ponc.repo.ISpiritualGrowthRepo;
import com.ponc.service.ISpiritualGrowthService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag( // agrupa en Swagger todos los endpoints de este controller
        name = "Crecimiento espiritual", // nombre del grupo que se ve en Swagger
        description = "Etapas de crecimiento espiritual disponibles")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/spiritualgrowths")
@AllArgsConstructor
public class SpiritualGrowthController {
    private final ISpiritualGrowthService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los crecimientos espirituales", // título corto que se ve en la lista de endpoints
            description = "Devuelve los crecimientos espirituales registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de crecimientos espirituales (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Crecimientos Espirituales.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<SpiritualGrowthDTO>> getAllSpiritualGrowth() throws Exception{
        List <SpiritualGrowthDTO> list = mapperUtil.mapList(service.findAll(),SpiritualGrowthDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un crecimiento espiritual por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el crecimiento espiritual que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del crecimiento espiritual", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El crecimiento espiritual encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el crecimiento espiritual con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Crecimiento Espiritual.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<SpiritualGrowthDTO> findById(@PathVariable("id") Integer id) throws Exception{
        SpiritualGrowthDTO objDTO=mapperUtil.map(service.findById(id),SpiritualGrowthDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un crecimiento espiritual nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un crecimiento espiritual nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El crecimiento espiritual se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear el Crecimiento Espiritual.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<SpiritualGrowthDTO> save(@Valid @RequestBody SpiritualGrowthDTO dto)throws Exception{
        dto.setState(true); // un registro nuevo siempre queda activo, aunque el JSON diga otra cosa o no traiga state
        SpiritualGrowth obj=service.save(mapperUtil.map(dto,SpiritualGrowth.class));
        SpiritualGrowthDTO objDTO=mapperUtil.map(obj,SpiritualGrowthDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un crecimiento espiritual existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del crecimiento espiritual que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del crecimiento espiritual", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El crecimiento espiritual se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el crecimiento espiritual con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Crecimiento Espiritual.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    // BORRADO: public ResponseEntity<SpiritualGrowthDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody SpiritualGrowthDTO dto)throws Exception{
    public ResponseEntity<SpiritualGrowthDTO> update(@PathVariable("id") Integer id,@Validated(OnUpdate.class) @RequestBody SpiritualGrowthDTO dto)throws Exception{
        // el @Valid ya no va en el id (no tiene reglas que revisar); el cuerpo se valida con las reglas normales y además exige state (grupo OnUpdate)
        SpiritualGrowth obj=service.update(mapperUtil.map(dto,SpiritualGrowth.class),id);
        SpiritualGrowthDTO objDTO=mapperUtil.map(obj,SpiritualGrowthDTO.class);
       return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un crecimiento espiritual", // título corto que se ve en la lista de endpoints
            description = "Elimina el crecimiento espiritual que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del crecimiento espiritual", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El crecimiento espiritual se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el crecimiento espiritual con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Crecimiento Espiritual.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Cambia el estado de un crecimiento espiritual", // título corto que se ve en la lista de endpoints
            description = "Activa o desactiva el crecimiento espiritual que tiene el id indicado. Solo cambia el estado (state); no modifica ningún otro dato.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del crecimiento espiritual", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El estado se cambió correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Falta el campo state o tiene un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el crecimiento espiritual con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al cambiar el estado del Crecimiento Espiritual.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PatchMapping("/{id}/state") // PATCH /spiritualgrowths/{id}/state: cambia solo una parte del registro (el estado)
    public ResponseEntity<SpiritualGrowthDTO> changeState(@PathVariable("id") Integer id, @Valid @RequestBody StateDTO dto) throws Exception { // el id viene en la URL; el cuerpo trae {"state": true/false} y @Valid exige que no falte
        SpiritualGrowth obj = service.changeState(id, dto.getState()); // el service cambia el estado y devuelve la entidad ya guardada
        SpiritualGrowthDTO objDTO = mapperUtil.map(obj, SpiritualGrowthDTO.class); // convierte la entidad al DTO que se devuelve
        return ResponseEntity.ok(objDTO); // responde 200 con el registro ya actualizado
    } // cierra el endpoint

}

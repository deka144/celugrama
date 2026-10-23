package com.ponc.controller;

import com.ponc.dto.OnUpdate;
import com.ponc.dto.PersonDTO;
import com.ponc.dto.StateDTO;
import com.ponc.model.Person;
import com.ponc.service.IPersonService;
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

import java.util.List;


@Tag( // agrupa en Swagger todos los endpoints de este controller
        name = "Personas", // nombre del grupo que se ve en Swagger
        description = "Datos personales de los miembros, invitados y líderes")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private final IPersonService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todas las personas", // título corto que se ve en la lista de endpoints
            description = "Devuelve las personas registradas.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de personas (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener las Personas.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll()throws Exception{
        List<PersonDTO> list =mapperUtil.mapList(service.findAll(), PersonDTO.class);
        return ResponseEntity.ok(list);
    }


    @Operation( // documenta este endpoint en Swagger
            summary = "Busca una persona por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve la persona que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la persona", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La persona encontrada"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe la persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar la Persona") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Integer id)throws Exception{
        PersonDTO objDTO= mapperUtil.map(service.findById(id), PersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra una persona nueva", // título corto que se ve en la lista de endpoints
            description = "Crea una persona nueva con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "La persona se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<PersonDTO> save (@Valid @RequestBody PersonDTO dto)throws Exception{
        dto.setState(true); // un registro nuevo siempre queda activo, aunque el JSON diga otra cosa o no traiga state
        Person obj=service.save(mapperUtil.map(dto,Person.class));
        PersonDTO objDTO=mapperUtil.map(obj,PersonDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza una persona existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos de la persona que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la persona", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La persona se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe la persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    // BORRADO: public ResponseEntity<PersonDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PersonDTO dto)throws Exception{
    public ResponseEntity<PersonDTO> update(@PathVariable("id") Integer id, @Validated(OnUpdate.class) @RequestBody PersonDTO dto)throws Exception{
        // el @Valid ya no va en el id (no tiene reglas que revisar); el cuerpo se valida con las reglas normales y además exige state (grupo OnUpdate)
        Person obj = service.update(mapperUtil.map(dto,Person.class),id);
        PersonDTO objDTO=mapperUtil.map(obj,PersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina una persona", // título corto que se ve en la lista de endpoints
            description = "Elimina la persona que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la persona", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "La persona se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe la persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Cambia el estado de una persona", // título corto que se ve en la lista de endpoints
            description = "Activa o desactiva la persona que tiene el id indicado. Solo cambia el estado (state); no modifica ningún otro dato.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la persona", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El estado se cambió correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Falta el campo state o tiene un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe la persona con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al cambiar el estado de la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PatchMapping("/{id}/state") // PATCH /persons/{id}/state: cambia solo una parte del registro (el estado)
    public ResponseEntity<PersonDTO> changeState(@PathVariable("id") Integer id, @Valid @RequestBody StateDTO dto) throws Exception { // el id viene en la URL; el cuerpo trae {"state": true/false} y @Valid exige que no falte
        Person obj = service.changeState(id, dto.getState()); // el service cambia el estado y devuelve la entidad ya guardada
        PersonDTO objDTO = mapperUtil.map(obj, PersonDTO.class); // convierte la entidad al DTO que se devuelve
        return ResponseEntity.ok(objDTO); // responde 200 con el registro ya actualizado
    } // cierra el endpoint

}

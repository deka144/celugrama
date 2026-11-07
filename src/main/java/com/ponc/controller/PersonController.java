package com.ponc.controller;

import com.ponc.dto.PersonResponseDTO;
import com.ponc.dto.PersonRequestDTO;
import com.ponc.dto.StateRequestDTO;
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
    public ResponseEntity<List<PersonResponseDTO>> findAll()throws Exception{
        List<PersonResponseDTO> list =mapperUtil.mapList(service.findAll(), PersonResponseDTO.class);
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
    public ResponseEntity<PersonResponseDTO> findById(@PathVariable("id") Integer id)throws Exception{
        PersonResponseDTO objDTO= mapperUtil.map(service.findById(id), PersonResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra una persona nueva", // título corto que se ve en la lista de endpoints
            description = "Crea una persona nueva con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "La persona se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "409", description = "«Ya existe una persona con ese número de documento.», «Ya existe una persona con ese correo.»"),
            // NUEVO: código 409: el dato ya existe (duplicado)
            @ApiResponse(responseCode = "500", description = "Error al crear la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<PersonResponseDTO> save (@Valid @RequestBody PersonRequestDTO dto)throws Exception{
        // CAMBIO: el cuerpo ahora es PersonRequestDTO: no trae id ni state
        Person newObj = mapperUtil.map(dto,Person.class); // NUEVO: primero se convierte el DTO en entidad, para poder poner el state aquí
        newObj.setState(true); // CAMBIO: antes era dto.setState(true). Un registro nuevo siempre queda activo; el DTO ya no trae state
        Person obj=service.save(newObj); // CAMBIO: guarda la entidad ya preparada
        PersonResponseDTO objDTO=mapperUtil.map(obj, PersonResponseDTO.class);
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
            @ApiResponse(responseCode = "409", description = "«Ya existe una persona con ese número de documento.», «Ya existe una persona con ese correo.»"),
            // NUEVO: código 409: el dato ya existe (duplicado)
            @ApiResponse(responseCode = "500", description = "Error al actualizar la Persona.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody PersonRequestDTO dto)throws Exception{
        Person obj = service.update(mapperUtil.map(dto,Person.class),id);
        PersonResponseDTO objDTO=mapperUtil.map(obj, PersonResponseDTO.class);
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
            @ApiResponse(responseCode = "409", description = "«No se puede eliminar la persona porque tiene un usuario asociado.»"),
            // NUEVO: código 409: otros registros lo usan, no se puede eliminar
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
    public ResponseEntity<PersonResponseDTO> changeState(@PathVariable("id") Integer id, @Valid @RequestBody StateRequestDTO dto) throws Exception { // el id viene en la URL; el cuerpo trae {"state": true/false} y @Valid exige que no falte
        Person obj = service.changeState(id, dto.getState()); // el service cambia el estado y devuelve la entidad ya guardada
        PersonResponseDTO objDTO = mapperUtil.map(obj, PersonResponseDTO.class); // convierte la entidad al DTO que se devuelve
        return ResponseEntity.ok(objDTO); // responde 200 con el registro ya actualizado
    } // cierra el endpoint

}

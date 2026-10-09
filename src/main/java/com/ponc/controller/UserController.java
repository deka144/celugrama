package com.ponc.controller;

import com.ponc.dto.UserDTO;
import com.ponc.model.User;
import com.ponc.service.IUserService;
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
        name = "Usuarios", // nombre del grupo que se ve en Swagger
        description = "Usuarios que ingresan al sistema")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserService service;
    private final MapperUtil mapperUtil;

    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los usuarios", // título corto que se ve en la lista de endpoints
            description = "Devuelve los usuarios registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de usuarios (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Usuarios.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() throws Exception{
        List<UserDTO> list=mapperUtil.mapList(service.findAll(),UserDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un usuario por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el usuario que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del usuario", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El usuario encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el usuario con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Usuario.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) throws Exception{
        UserDTO objDTO=mapperUtil.map(service.findById(id),UserDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un usuario nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un usuario nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El usuario se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear el Usuario.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO dto) throws Exception{

        User obj=service.save(mapperUtil.map(dto,User.class));
        UserDTO objDTO=mapperUtil.map(obj,UserDTO.class);

        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un usuario existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del usuario que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del usuario", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El usuario se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el usuario con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Usuario.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody UserDTO dto) throws Exception{
        User obj=service.update(mapperUtil.map(dto,User.class),id);
        UserDTO objDTO=mapperUtil.map(obj,UserDTO.class);

        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un usuario", // título corto que se ve en la lista de endpoints
            description = "Elimina el usuario que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del usuario", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El usuario se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el usuario con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Usuario.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.ponc.controller;

import com.ponc.dto.RoleDTO;
import com.ponc.model.Role;
import com.ponc.service.IRoleService;
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
        name = "Roles", // nombre del grupo que se ve en Swagger
        description = "Roles de usuario del sistema")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private final IRoleService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los roles", // título corto que se ve en la lista de endpoints
            description = "Devuelve los roles registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de roles (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Roles.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() throws Exception{
        List<RoleDTO> list = mapperUtil.mapList(service.findAll(), RoleDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un rol por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el rol que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del rol", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El rol encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el rol con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Rol.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable("id") Integer id )throws Exception{
        Role obj = service.findById(id);
        RoleDTO objDto =mapperUtil.map(obj, RoleDTO.class);
        return ResponseEntity.ok(objDto);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un rol nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un rol nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El rol se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear el Role.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<RoleDTO>  save(@Valid @RequestBody RoleDTO dto)throws Exception{
        Role obj = service.save(mapperUtil.map(dto, Role.class));
        RoleDTO objDTO= mapperUtil.map(obj,RoleDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un rol existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del rol que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del rol", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El rol se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el rol con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Rol.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update (@Valid @PathVariable("id") Integer id,@RequestBody RoleDTO dto)throws  Exception{
        Role obj = service.update(mapperUtil.map(dto, Role.class),id);
        RoleDTO objDTO= mapperUtil.map(obj, RoleDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un rol", // título corto que se ve en la lista de endpoints
            description = "Elimina el rol que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del rol", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El rol se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el rol con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Rol.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id )throws  Exception{
            service.deleteById(id);
            return ResponseEntity.noContent().build();
    }
}

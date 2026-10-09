package com.ponc.controller;

import com.ponc.dto.MenuDTO;
import com.ponc.model.Menu;
import com.ponc.service.IMenuService;
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
        name = "Menús", // nombre del grupo que se ve en Swagger
        description = "Opciones del menú de la aplicación")
// explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/menus")
@AllArgsConstructor
public class MenuController {
    private final IMenuService service;
    private final MapperUtil mapperUtil;

    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los menús", // título corto que se ve en la lista de endpoints
            description = "Devuelve los menús registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de menús (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los menus.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<MenuDTO>> findAll()throws Exception{
        List<MenuDTO> list =mapperUtil.mapList(service.findAll(),MenuDTO.class);
        return ResponseEntity.ok(list);
    }


    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un menú por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el menú que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del menú", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El menú encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el menú con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el menu.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> findById(@PathVariable("id") Integer id)throws Exception{
        MenuDTO objDTO=mapperUtil.map(service.findById(id),MenuDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un menú nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un menú nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El menú se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear el menu.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<MenuDTO> save(@Valid @RequestBody MenuDTO dto)throws Exception{
        Menu obj=service.save(mapperUtil.map(dto,Menu.class));
        MenuDTO objDTO=mapperUtil.map(obj,MenuDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un menú existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del menú que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del menú", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El menú se actualizó correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el menú con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el menu.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody MenuDTO dto)throws Exception{
        Menu obj =service.update(mapperUtil.map(dto,Menu.class),id);
        MenuDTO objDTO=mapperUtil.map(obj,MenuDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un menú", // título corto que se ve en la lista de endpoints
            description = "Elimina el menú que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del menú", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El menú se eliminó (no devuelve contenido)"), // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el menú con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el menu.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id )throws Exception {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.ponc.controller;

import com.ponc.dto.PageResponseDTO;
import com.ponc.dto.StateRequestDTO;
import com.ponc.dto.UserResponseDTO;
import com.ponc.dto.UserRequestDTO;
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
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<UserResponseDTO>> findAll() throws Exception{
        List<UserResponseDTO> list=mapperUtil.mapList(service.findAll(), UserResponseDTO.class);
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
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") Integer id) throws Exception{
        UserResponseDTO objDTO=mapperUtil.map(service.findById(id), UserResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un usuario nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un usuario nuevo con los datos que se envían en el cuerpo (JSON).") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El usuario se creó correctamente"), // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "409", description = "«Ya existe un usuario con ese username.»"), // NUEVO: código 409: el dato ya existe (duplicado)
            @ApiResponse(responseCode = "500", description = "Error al crear el Usuario.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody UserRequestDTO dto) throws Exception{
        // CAMBIO: el cuerpo ahora es UserRequestDTO: no trae id ni state
        User newObj = mapperUtil.map(dto,User.class); // NUEVO: primero se convierte el DTO en entidad, para poder poner el state aquí
        newObj.setState(true); // CAMBIO: antes era dto.setState(true). Un registro nuevo siempre queda activo; el DTO ya no trae state
        User obj=service.save(newObj); // CAMBIO: guarda la entidad ya preparada
        UserResponseDTO objDTO=mapperUtil.map(obj, UserResponseDTO.class);

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
            @ApiResponse(responseCode = "409", description = "«Ya existe un usuario con ese username.»"), // NUEVO: código 409: el dato ya existe (duplicado)
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Usuario.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody UserRequestDTO dto) throws Exception{
        User obj=service.update(mapperUtil.map(dto,User.class),id);
        UserResponseDTO objDTO=mapperUtil.map(obj, UserResponseDTO.class);
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

    @Operation( // documenta este endpoint en Swagger
            summary = "Cambia el estado de un usuario", // título corto que se ve en la lista de endpoints
            description = "Activa o desactiva el usuario que tiene el id indicado. Solo cambia el estado (state); no modifica ningún otro dato.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del usuario", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El estado se cambió correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Falta el campo state o tiene un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el usuario con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al cambiar el estado del Usuario.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PatchMapping("/{id}/state") // PATCH /users/{id}/state: cambia solo una parte del registro (el estado)
    public ResponseEntity<UserResponseDTO> changeState(@PathVariable("id") Integer id, @Valid @RequestBody StateRequestDTO dto) throws Exception { // el id viene en la URL; el cuerpo trae {"state": true/false} y @Valid exige que no falte
        User obj = service.changeState(id, dto.getState()); // el service cambia el estado y devuelve la entidad ya guardada
        UserResponseDTO objDTO = mapperUtil.map(obj, UserResponseDTO.class); // convierte la entidad al DTO que se devuelve
        return ResponseEntity.ok(objDTO); // responde 200 con el registro ya actualizado
    } // cierra el endpoint

    @Operation( // PAGINADO: documenta este endpoint en Swagger
            summary = "Lista los usuarios por páginas, con búsqueda opcional", // PAGINADO: título corto que se ve en la lista de endpoints
            description = "Devuelve una página de usuarios. Sin search trae todos; con search filtra por username, nombre, apellidos o número de documento de la persona.", // PAGINADO: explicación que se ve al abrir el endpoint
            parameters = { // PAGINADO: explica los parámetros del endpoint
                    @Parameter(name = "search", in = ParameterIn.QUERY, description = "Texto a buscar (opcional)", example = "juan"), // PAGINADO: texto de búsqueda; va en la URL (QUERY)
                    @Parameter(name = "page", in = ParameterIn.QUERY, description = "Número de página; la primera es la 0", example = "0"), // PAGINADO: página pedida
                    @Parameter(name = "size", in = ParameterIn.QUERY, description = "Registros por página (máximo 100)", example = "10") // PAGINADO: tamaño de la página
            }) // PAGINADO: cierra la lista de parámetros y el @Operation
    @ApiResponses({ // PAGINADO: respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La página pedida (content vacío si no hay coincidencias)"), // PAGINADO: código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Usuarios.") // PAGINADO: código 500: error interno
    }) // PAGINADO: cierra la lista de respuestas
    @GetMapping("/page") // PAGINADO: GET /users/page?search=&page=0&size=10; no choca con /{id} porque "page" no es un número
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> findPage( // PAGINADO: devuelve la página dentro de PageResponseDTO
                                                                      @RequestParam(name = "search", required = false) String search, // PAGINADO: texto a buscar; si no se envía, no filtra
                                                                      @RequestParam(name = "page", defaultValue = "0") int page, // PAGINADO: número de página; por defecto la primera (0)
                                                                      @RequestParam(name = "size", defaultValue = "10") int size) throws Exception { // PAGINADO: registros por página; por defecto 10
        Page<UserResponseDTO> result = service.findPage(search, page, size) // PAGINADO: pide la página de entidades al service
                .map(e -> mapperUtil.map(e, UserResponseDTO.class)); // PAGINADO: convierte cada entidad a su DTO de salida
        return ResponseEntity.ok(PageResponseDTO.from(result)); // PAGINADO: responde 200 con la página; ApiResponseAdvice la envuelve en data
    }


}

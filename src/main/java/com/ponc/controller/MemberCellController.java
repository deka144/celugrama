package com.ponc.controller;

import com.ponc.dto.*;
import com.ponc.model.MemberCell;
import com.ponc.service.IMemberCellService;
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
        name = "Miembros de célula", // nombre del grupo que se ve en Swagger
        description = "Líderes y discípulos de cada célula") // explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/membercells")
@AllArgsConstructor
public class MemberCellController {
    private final IMemberCellService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los miembros de célula", // título corto que se ve en la lista de endpoints
            description = "Devuelve los miembros de célula registrados.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de miembros de célula (vacía si no hay ninguna)"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Miembros de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<MemberCellResponseDTO>> findAll()throws Exception{
        List<MemberCellResponseDTO> list = mapperUtil.mapList(service.findAll(), MemberCellResponseDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un miembro de célula por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el miembro de célula que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del miembro de célula", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El miembro de célula encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el miembro de célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Miembro de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<MemberCellResponseDTO> findById(@PathVariable("id") Integer id)throws Exception{
        MemberCellResponseDTO objDTO= mapperUtil.map(service.findById(id), MemberCellResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un miembro de célula nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un miembro de célula nuevo con los datos que se envían en el cuerpo (JSON). Reglas: " +
                    "una célula solo puede tener un Líder activo; una persona no puede ser Discípulo de dos células; " +
                    "un Líder no puede liderar dos células de Discipulado, ni dos células el mismo día " +
                    "con menos de 2 horas de diferencia; un Discípulo de una célula de Discipulado " +
                    "debe ser Líder activo de otra, y esa célula admite como máximo 12 Discípulos. " +
                    "También deja el movimiento en el historial de la persona.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El miembro de célula se creó correctamente"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno. " +
                    "El mensaje viene en errorMessage, por ejemplo: «Esta célula ya tiene un Líder activo.»," +
                    " «Esta persona ya es Discípulo de otra célula.», «Este Líder ya lidera " +
                    "otra célula de tipo Discipulado.», «Ya lideras otra célula el mismo día, " +
                    "con menos de 2 horas de diferencia (<hora>).», " +
                    "«Este Discípulo tiene que ser Líder activo de otra célula.», " +
                    "«Esta célula de Discipulado ya tiene el máximo de 12 Discípulos.», " +
                    "«Error al crear el Miembro de Célula.».") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<MemberCellResponseDTO> save(@Valid @RequestBody MemberCellRequestDTO dto)throws Exception{
        MemberCell newObj = mapperUtil.map(dto,MemberCell.class); // se convierte el DTO en entidad, para poder poner el state aquí
        newObj.setState(true); //  el DTO ya no trae state
        MemberCell obj=service.save(newObj); //  guarda la entidad ya preparada
        MemberCellResponseDTO objDTO=mapperUtil.map(obj, MemberCellResponseDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un miembro de célula existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del miembro de célula que tiene el id indicado, con lo que se envía " +
                    "en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del miembro de célula",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El miembro de célula se actualizó correctamente"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el miembro de célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Miembro de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<MemberCellResponseDTO> update(@PathVariable("id")Integer id, @Valid @RequestBody MemberCellRequestDTO dto)throws Exception{
        MemberCell obj=service.update(mapperUtil.map(dto, MemberCell.class),id);
        MemberCellResponseDTO objDTO=mapperUtil.map(obj, MemberCellResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un miembro de célula", // título corto que se ve en la lista de endpoints
            description = "Elimina el miembro de célula que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del miembro de célula", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El miembro de célula se eliminó (no devuelve contenido)"),
            // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el miembro de célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "409", description = "«No se puede eliminar el miembro de célula porque tiene detalles de asistencia registrados.»"),
            // NUEVO: código 409: otros registros lo usan, no se puede eliminar
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Miembro de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Requerimiento 15: lista de celulas activas donde una persona
    // es Lider, para que el frontend la muestre antes de elegir.
    @Operation( // documenta este endpoint en Swagger
            summary = "Lista las células que lidera una persona", // título corto que se ve en la lista de endpoints
            description = "Devuelve las células activas donde la persona es Líder, con su tipo, día y hora. " +
                    "Sirve para que el Líder elija entre sus células.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "idPerson", in = ParameterIn.PATH, description = "Id de la persona (Person)",
                            example = "1") // parámetro 'idPerson': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de células del Líder (vacía si no lidera ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener las células del Líder.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/lider/{idPerson}") // GET /membercells/lider/{idPerson}
    public ResponseEntity<List<LeaderCellResponseDTO>> findLeaderCells(@PathVariable("idPerson") Integer idPerson) throws Exception { // idPerson viene en la URL
        return ResponseEntity.ok(service.findLeaderCells(idPerson));
        // delega en el service y responde 200 con la lista
    } // cierra el endpoint

    @Operation( // documenta este endpoint en Swagger
            summary = "Cambia el estado de un miembro de célula", // título corto que se ve en la lista de endpoints
            description = "Activa o desactiva el miembro de célula que tiene el id indicado. Solo cambia el estado (state); no modifica ningún otro dato.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del miembro de célula", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El estado se cambió correctamente"), // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Falta el campo state o tiene un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el miembro de célula con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al cambiar el estado del Miembro de Célula.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PatchMapping("/{id}/state") // PATCH /membercells/{id}/state: cambia solo una parte del registro (el estado)
    public ResponseEntity<MemberCellResponseDTO> changeState(@PathVariable("id") Integer id, @Valid @RequestBody StateRequestDTO dto) throws Exception { // el id viene en la URL; el cuerpo trae {"state": true/false} y @Valid exige que no falte
        MemberCell obj = service.changeState(id, dto.getState()); // el service cambia el estado y devuelve la entidad ya guardada
        MemberCellResponseDTO objDTO = mapperUtil.map(obj, MemberCellResponseDTO.class); // convierte la entidad al DTO que se devuelve
        return ResponseEntity.ok(objDTO); // responde 200 con el registro ya actualizado
    } // cierra el endpoint

    @Operation( // PAGINADO: documenta este endpoint en Swagger
            summary = "Lista los miembros de célula por páginas, con búsqueda opcional", // PAGINADO: título corto que se ve en la lista de endpoints
            description = "Devuelve una página de miembros de célula. Sin search trae todos; con search filtra por nombre o apellidos de la persona.", // PAGINADO: explicación que se ve al abrir el endpoint
            parameters = { // PAGINADO: explica los parámetros del endpoint
                    @Parameter(name = "search", in = ParameterIn.QUERY, description = "Texto a buscar (opcional)", example = "juan"), // PAGINADO: texto de búsqueda; va en la URL (QUERY)
                    @Parameter(name = "page", in = ParameterIn.QUERY, description = "Número de página; la primera es la 0", example = "0"), // PAGINADO: página pedida
                    @Parameter(name = "size", in = ParameterIn.QUERY, description = "Registros por página (máximo 100)", example = "10") // PAGINADO: tamaño de la página
            }) // PAGINADO: cierra la lista de parámetros y el @Operation
    @ApiResponses({ // PAGINADO: respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La página pedida (content vacío si no hay coincidencias)"), // PAGINADO: código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Miembros de célula.") // PAGINADO: código 500: error interno
    }) // PAGINADO: cierra la lista de respuestas
    @GetMapping("/page") // PAGINADO: GET /membercells/page?search=&page=0&size=10; no choca con /{id} porque "page" no es un número
    public ResponseEntity<PageResponseDTO<MemberCellResponseDTO>> findPage( // PAGINADO: devuelve la página dentro de PageResponseDTO
                                                                            @RequestParam(name = "search", required = false) String search, // PAGINADO: texto a buscar; si no se envía, no filtra
                                                                            @RequestParam(name = "page", defaultValue = "0") int page, // PAGINADO: número de página; por defecto la primera (0)
                                                                            @RequestParam(name = "size", defaultValue = "10") int size) throws Exception { // PAGINADO: registros por página; por defecto 10
        Page<MemberCellResponseDTO> result = service.findPage(search, page, size) // PAGINADO: pide la página de entidades al service
                .map(e -> mapperUtil.map(e, MemberCellResponseDTO.class)); // PAGINADO: convierte cada entidad a su DTO de salida
        return ResponseEntity.ok(PageResponseDTO.from(result)); // PAGINADO: responde 200 con la página; ApiResponseAdvice la envuelve en data
    }


}

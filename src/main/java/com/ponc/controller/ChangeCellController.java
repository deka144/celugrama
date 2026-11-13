package com.ponc.controller;

import com.ponc.dto.*;
import com.ponc.model.ChangeCell;
import com.ponc.service.IChangeCellService;
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
        name = "Cambios de célula", // nombre del grupo que se ve en Swagger
        description = "Cambios de célula y de Líder, con su historial") // explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/ChangeCells")
@AllArgsConstructor
public class ChangeCellController {
    private final IChangeCellService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los cambios de célula", // título corto que se ve en la lista de endpoints
            description = "Devuelve los cambios de célula registrados.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de cambios de célula (vacía si no hay ninguna)"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Cambios de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<ChangeCellResponseDTO>> findAll()throws Exception{
        List<ChangeCellResponseDTO> list = mapperUtil.mapList(service.findAll(), ChangeCellResponseDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un cambio de célula por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el cambio de célula que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del cambio de célula",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El cambio de célula encontrado"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el cambio de célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Cambio de Célula")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<ChangeCellResponseDTO> findById(@PathVariable("id") Integer id)throws Exception{
        ChangeCellResponseDTO objDTO=mapperUtil.map(service.findById(id), ChangeCellResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un cambio de célula nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un cambio de célula nuevo con los datos que se envían en el cuerpo (JSON).")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El cambio de célula se creó correctamente"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear el Cambio de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<ChangeCellResponseDTO> save(@Valid @RequestBody ChangeCellSaveRequestDTO dto )throws Exception{
        ChangeCell obj=service.save(mapperUtil.map(dto, ChangeCell.class));
        ChangeCellResponseDTO objDTO=mapperUtil.map(obj, ChangeCellResponseDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    // Punto 8: nuevo endpoint que orquesta el cambio de celula completo
    // (desactiva la vieja, crea la nueva, crea el ChangeCell y el
    // HistoryPerson), en vez de armar el ChangeCell a mano con el
    // POST /ChangeCells de arriba.
    @Operation( // documenta este endpoint en Swagger
            summary = "Cambia a un miembro de célula", // título corto que se ve en la lista de endpoints
            description = "Da de baja al miembro en su célula actual, lo registra en la célula nueva con " +
                    "el mismo rol y deja el cambio en el historial, todo en una sola operación. " +
                    "En el cuerpo se envía idMemberCellOld (el miembro actual) e idCellNew (la célula destino). " +
                    "Aplican las mismas reglas que al registrar un miembro nuevo.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "Cambio de célula registrado"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Falta idMemberCellOld o idCellNew."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno. " +
                    "El mensaje viene en errorMessage, por ejemplo: «El Miembro de Célula indicado no Existe», " +
                    "«Esta célula ya tiene un Líder activo.»")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping("/cambiar-celula")
    public ResponseEntity<ChangeCellResponseDTO> changeCell(@Valid @RequestBody ChangeCellRequestDTO dto) throws Exception {
        ChangeCell obj = service.changeCell(dto.getIdMemberCellOld(), dto.getIdCellNew());
        ChangeCellResponseDTO objDTO = mapperUtil.map(obj, ChangeCellResponseDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    // Punto 12: nuevo endpoint que orquesta el cambio de Lider de una
    // celula completo (desactiva el viejo, crea el nuevo, crea el
    // ChangeCell y el HistoryPerson).
    @Operation( // documenta este endpoint en Swagger
            summary = "Cambia el Líder de una célula", // título corto que se ve en la lista de endpoints
            description = "Da de baja al Líder actual y registra a la persona indicada como nuevo Líder de " +
                    "la misma célula, y deja el cambio en el historial. En el cuerpo se envía idMemberCellOldLeader " +
                    "(el Líder actual) e idPersonNewLeader (la persona que lo reemplaza).")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "Cambio de Líder registrado"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Falta idMemberCellOldLeader o idPersonNewLeader."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno. " +
                    "El mensaje viene en errorMessage, por ejemplo: «El Miembro de Célula indicado no existe.», " +
                    "«Este Líder ya lidera otra célula de tipo Discipulado.»")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping("/cambiar-lider")
    public ResponseEntity<ChangeCellResponseDTO> changeCellLeader(@Valid @RequestBody ChangeCellLeaderRequestDTO dto) throws Exception {
        ChangeCell obj = service.changeCellLeader(dto.getIdMemberCellOldLeader(), dto.getIdPersonNewLeader());
        ChangeCellResponseDTO objDTO = mapperUtil.map(obj, ChangeCellResponseDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }


    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un cambio de célula existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del cambio de célula que tiene el id indicado, " +
                    "con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del cambio de célula",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El cambio de célula se actualizó correctamente"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el cambio de célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el Cambio de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<ChangeCellResponseDTO> update (@PathVariable("id") Integer id, @Valid @RequestBody ChangeCellSaveRequestDTO dto )throws Exception{
        // el @Valid ya no va en el id (no tiene reglas que revisar); ahora va en el cuerpo, que es lo que se valida
        ChangeCell obj=service.update(mapperUtil.map(dto, ChangeCell.class),id);
        ChangeCellResponseDTO objDTO=mapperUtil.map(obj, ChangeCellResponseDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un cambio de célula", // título corto que se ve en la lista de endpoints
            description = "Elimina el cambio de célula que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del cambio de célula",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El cambio de célula se eliminó (no devuelve contenido)"),
            // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el cambio de célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Cambio de Célula.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation( // PAGINADO: documenta este endpoint en Swagger
            summary = "Lista los cambios de célula por páginas, con búsqueda opcional", // PAGINADO: título corto que se ve en la lista de endpoints
            description = "Devuelve una página de cambios de célula. Sin search trae todos; con search filtra por nombre o apellidos de la persona del miembro anterior o del nuevo.", // PAGINADO: explicación que se ve al abrir el endpoint
            parameters = { // PAGINADO: explica los parámetros del endpoint
                    @Parameter(name = "search", in = ParameterIn.QUERY, description = "Texto a buscar (opcional)", example = "juan"), // PAGINADO: texto de búsqueda; va en la URL (QUERY)
                    @Parameter(name = "page", in = ParameterIn.QUERY, description = "Número de página; la primera es la 0", example = "0"), // PAGINADO: página pedida
                    @Parameter(name = "size", in = ParameterIn.QUERY, description = "Registros por página (máximo 100)", example = "10") // PAGINADO: tamaño de la página
            }) // PAGINADO: cierra la lista de parámetros y el @Operation
    @ApiResponses({ // PAGINADO: respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La página pedida (content vacío si no hay coincidencias)"), // PAGINADO: código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los Cambios de célula.") // PAGINADO: código 500: error interno
    }) // PAGINADO: cierra la lista de respuestas
    @GetMapping("/page") // PAGINADO: GET /ChangeCells/page?search=&page=0&size=10; no choca con /{id} porque "page" no es un número
    public ResponseEntity<PageResponseDTO<ChangeCellResponseDTO>> findPage( // PAGINADO: devuelve la página dentro de PageResponseDTO
                                                                            @RequestParam(name = "search", required = false) String search, // PAGINADO: texto a buscar; si no se envía, no filtra
                                                                            @RequestParam(name = "page", defaultValue = "0") int page, // PAGINADO: número de página; por defecto la primera (0)
                                                                            @RequestParam(name = "size", defaultValue = "10") int size) throws Exception { // PAGINADO: registros por página; por defecto 10
        Page<ChangeCellResponseDTO> result = service.findPage(search, page, size) // PAGINADO: pide la página de entidades al service
                .map(e -> mapperUtil.map(e, ChangeCellResponseDTO.class)); // PAGINADO: convierte cada entidad a su DTO de salida
        return ResponseEntity.ok(PageResponseDTO.from(result)); // PAGINADO: responde 200 con la página; ApiResponseAdvice la envuelve en data
    }


}

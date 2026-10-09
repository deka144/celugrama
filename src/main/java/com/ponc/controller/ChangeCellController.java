package com.ponc.controller;

import com.ponc.dto.ChangeCellDTO;
import com.ponc.dto.ChangeCellLeaderRequestDTO;
import com.ponc.dto.ChangeCellRequestDTO;
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
import org.apache.coyote.Response;
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
    public ResponseEntity<List<ChangeCellDTO>> findAll()throws Exception{
        List<ChangeCellDTO> list = mapperUtil.mapList(service.findAll(), ChangeCellDTO.class);
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
    public ResponseEntity<ChangeCellDTO> findById(@PathVariable("id") Integer id)throws Exception{
        ChangeCellDTO objDTO=mapperUtil.map(service.findById(id),ChangeCellDTO.class);
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
    public ResponseEntity<ChangeCellDTO> save(@Valid @RequestBody ChangeCellDTO dto )throws Exception{
        ChangeCell obj=service.save(mapperUtil.map(dto, ChangeCell.class));
        ChangeCellDTO objDTO=mapperUtil.map(obj,ChangeCellDTO.class);
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
    public ResponseEntity<ChangeCellDTO> changeCell(@Valid @RequestBody ChangeCellRequestDTO dto) throws Exception {
        ChangeCell obj = service.changeCell(dto.getIdMemberCellOld(), dto.getIdCellNew());
        ChangeCellDTO objDTO = mapperUtil.map(obj, ChangeCellDTO.class);
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
    public ResponseEntity<ChangeCellDTO> changeCellLeader(@Valid @RequestBody ChangeCellLeaderRequestDTO dto) throws Exception {
        ChangeCell obj = service.changeCellLeader(dto.getIdMemberCellOldLeader(), dto.getIdPersonNewLeader());
        ChangeCellDTO objDTO = mapperUtil.map(obj, ChangeCellDTO.class);
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
    public ResponseEntity<ChangeCellDTO> update (@Valid @PathVariable("id") Integer id, @RequestBody ChangeCellDTO dto )throws Exception{
        ChangeCell obj=service.update(mapperUtil.map(dto, ChangeCell.class),id);
        ChangeCellDTO objDTO=mapperUtil.map(obj, ChangeCellDTO.class);
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

}

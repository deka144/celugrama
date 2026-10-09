package com.ponc.controller;

import com.ponc.dto.CellDTO;
import com.ponc.dto.DiscipleshipCompositionResponseDTO;
import com.ponc.model.Cell;
import com.ponc.service.ICellService;
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
        name = "Células", // nombre del grupo que se ve en Swagger
        description = "Células de evangelismo y discipulado") // explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/cells")
@AllArgsConstructor
public class CellController {

    private final ICellService service;
    private final MapperUtil mapperUtil;

    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todas las células", // título corto que se ve en la lista de endpoints
            description = "Devuelve las células registradas.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de células (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener Célula") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<CellDTO>> findAll()throws Exception{
        List<CellDTO> list = mapperUtil.mapList(service.findAll(), CellDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca una célula por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve la célula que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la célula", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La célula encontrada"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe la célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar Célula")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<CellDTO> findById(@PathVariable("id") Integer id)throws Exception{
        CellDTO objDTO=mapperUtil.map(service.findById(id), CellDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra una célula nueva", // título corto que se ve en la lista de endpoints
            description = "Crea una célula nueva con los datos que se envían en el cuerpo (JSON)." +
                    " El día de la célula se envía como uno de estos valores: LUNES, MARTES, MIERCOLES, JUEVES, " +
                    "VIERNES, SABADO o DOMINGO.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "La célula se creó correctamente"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Error al crear Célula")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<CellDTO> save(@Valid @RequestBody CellDTO dto)throws Exception{
        Cell obj= service.save(mapperUtil.map(dto,Cell.class));
        CellDTO objDTO=mapperUtil.map(obj,CellDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza una célula existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos de la célula que tiene el id indicado, con lo que se envía " +
                    "en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la célula", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La célula se actualizó correctamente"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe la célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar Célula")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<CellDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody CellDTO dto)throws Exception{
        Cell obj=service.update(mapperUtil.map(dto,Cell.class),id);
        CellDTO objDto=mapperUtil.map(obj,CellDTO.class);
        return ResponseEntity.ok(objDto);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina una célula", // título corto que se ve en la lista de endpoints
            description = "Elimina la célula que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la célula", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "La célula se eliminó (no devuelve contenido)"),
            // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe la célula con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar Célula")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // Punto 13: reporte de composicion de una celula de Discipulado,
    // para la notificacion despues de un cambio de Lider (punto 12)
    // o del cierre de una celula.
    @Operation( // documenta este endpoint en Swagger
            summary = "Verifica la composición de una célula de Discipulado",
            // título corto que se ve en la lista de endpoints
            description = "Informa cuántos Discípulos activos tiene la célula," +
                    " cuántos de ellos siguen liderando otra célula y si se cumple el mínimo de 6. Sirve para avisar después de un cambio de Líder o del cierre de una célula. Solo funciona con células de tipo Discipulado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la célula de Discipulado",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Composición de la célula (Discípulos activos y " +
                    "si cumple el mínimo)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno. " +
                    "El mensaje viene en errorMessage, por ejemplo: «La célula indicada no existe.»," +
                    " «Esta célula no es de tipo Discipulado.»")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}/composicion-discipulado")
    public ResponseEntity<DiscipleshipCompositionResponseDTO> verifyDiscipleshipComposition(@PathVariable("id") Integer id) throws Exception {
        return ResponseEntity.ok(service.verifyDiscipleshipComposition(id));
    }

}

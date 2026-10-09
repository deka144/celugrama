package com.ponc.controller;

import com.ponc.dto.GuestDTO;
import com.ponc.model.Guest;
import com.ponc.service.IGuestService;
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
        name = "Invitados", // nombre del grupo que se ve en Swagger
        description = "Asistencia de invitados a las células") // explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/Guests")
@AllArgsConstructor
public class GuestController {
    private final IGuestService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los invitados", // título corto que se ve en la lista de endpoints
            description = "Devuelve los invitados registrados.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de invitados (vacía si no hay ninguna)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los invitados.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<GuestDTO>> findAll()throws Exception{
        List<GuestDTO> list =mapperUtil.mapList(service.findAll(),GuestDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un invitado por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el invitado que tiene el id indicado.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del invitado", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El invitado encontrado"), // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el invitado con ese id."), // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar el Invitado.") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> findById(@PathVariable("id") Integer id)throws Exception{
        GuestDTO objDTO=mapperUtil.map(service.findById(id),GuestDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un invitado nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un invitado nuevo con los datos que se envían en el cuerpo (JSON). " +
                    "Solo se puede registrar el día en que se realiza la célula y dentro de las 4 horas " +
                    "siguientes a su hora de inicio. Una persona solo puede registrarse una vez como invitado " +
                    "por periodo y no puede ser miembro de una célula. Si completa 5 semanas seguidas como invitado " +
                    "en la misma célula, pasa a ser Discípulo automáticamente.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El invitado se creó correctamente"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."), // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno. " +
                    "El mensaje viene en errorMessage, por ejemplo: «La célula indicada no existe.»," +
                    " «Solo se puede registrar el día que se realiza la célula (<día>).», " +
                    "«Todavía no es la hora de la célula (empieza a las <hora>).», " +
                    "«Ya pasó la ventana de 4 horas para registrar esta célula.», " +
                    "«Esta persona ya registró asistencia como invitado en este periodo.», " +
                    "«Esta persona ya es miembro de una célula, no puede registrarse como invitado.», " +
                    "«Error al crear el Invitado».") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<GuestDTO> save (@Valid @RequestBody GuestDTO dto)throws Exception{
        Guest obj=service.save(mapperUtil.map(dto, Guest.class));
        GuestDTO objDTO=mapperUtil.map(obj, GuestDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un invitado existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del invitado que tiene el id indicado, con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del invitado", example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El invitado se actualizó correctamente"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el invitado con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar el invitado.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> update (@Valid @PathVariable("id") Integer id, @RequestBody GuestDTO dto)throws Exception{
        Guest obj=service.update(mapperUtil.map(dto, Guest.class),id);
        GuestDTO objDTO=mapperUtil.map(obj,GuestDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un invitado", // título corto que se ve en la lista de endpoints
            description = "Elimina el invitado que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del invitado", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El invitado se eliminó (no devuelve contenido)"),
            // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el invitado con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar el Invitado.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

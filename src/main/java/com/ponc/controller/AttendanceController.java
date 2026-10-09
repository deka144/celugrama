package com.ponc.controller;

import com.ponc.dto.AttendanceDTO;
import com.ponc.model.Attendance;
import com.ponc.service.IAttendanceService;
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
        name = "Asistencias", // nombre del grupo que se ve en Swagger
        description = "Asistencia de cada célula por periodo") // explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/attendances")
@AllArgsConstructor
public class AttendanceController {
//todo ok con Git multiLogin
    private final IAttendanceService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todas las asistencias", // título corto que se ve en la lista de endpoints
            description = "Devuelve las asistencias registradas.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de asistencias (vacía si no hay ninguna)"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener las asistencias")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> findAll () throws Exception{
        List<AttendanceDTO> list=mapperUtil.mapList(service.findAll(), AttendanceDTO.class);
        return ResponseEntity.ok().body(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca una asistencia por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve la asistencia que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la asistencia", example = "1")
                    // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La asistencia encontrada"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe la asistencia con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar asistencia")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> findById(@PathVariable("id") Integer id)throws Exception{
        AttendanceDTO objDTO=mapperUtil.map(service.findById(id), AttendanceDTO.class);
        return ResponseEntity.ok().body(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra una asistencia nueva", // título corto que se ve en la lista de endpoints
            description = "Crea una asistencia nueva con los datos que se envían en el cuerpo (JSON). " +
                    "Solo se puede registrar el día en que se realiza la célula y dentro de las 4 horas " +
                    "siguientes a su hora de inicio.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "La asistencia se creó correctamente"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno. " +
                    "El mensaje viene en errorMessage, por ejemplo: «La célula indicada no existe.», " +
                    "«Solo se puede registrar el día que se realiza la célula (<día>).», " +
                    "«Todavía no es la hora de la célula (empieza a las <hora>).»," +
                    " «Ya pasó la ventana de 4 horas para registrar esta célula.»," +
                    " «Error al crear Asistencia».") // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<AttendanceDTO> save (@Valid @RequestBody AttendanceDTO dto)throws Exception{
        Attendance obj= service.save(mapperUtil.map(dto, Attendance.class));
        AttendanceDTO objDTO=mapperUtil.map(obj, AttendanceDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza una asistencia existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos de la asistencia que tiene el id indicado, " +
                    "con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la asistencia",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "La asistencia se actualizó correctamente"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe la asistencia con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar  Asistencia")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody AttendanceDTO dto)throws Exception{
        Attendance obj =service.update(mapperUtil.map(dto,Attendance.class),id);
        AttendanceDTO objDTO=mapperUtil.map(obj, AttendanceDTO.class);
        return ResponseEntity.ok().body(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina una asistencia", // título corto que se ve en la lista de endpoints
            description = "Elimina la asistencia que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id de la asistencia",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "La asistencia se eliminó (no devuelve contenido)"),
            // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe la asistencia con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar asistencia")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

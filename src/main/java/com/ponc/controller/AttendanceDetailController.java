package com.ponc.controller;

import com.ponc.dto.AttendanceDetailDTO;
import com.ponc.model.AttendanceDetail;
import com.ponc.service.IAttendanceDetailService;
import com.ponc.util.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag( // agrupa en Swagger todos los endpoints de este controller
        name = "Detalle de asistencia", // nombre del grupo que se ve en Swagger
        description = "Registro de asistencia por miembro y reportes de inasistencias") // explicación del grupo (cierra el @Tag)
@RestController
@RequestMapping("/attendancedetail")
@AllArgsConstructor
public class AttendanceDetailController {

    private final IAttendanceDetailService service;
    private final MapperUtil mapperUtil;


    @Operation( // documenta este endpoint en Swagger
            summary = "Lista todos los detalles de asistencia", // título corto que se ve en la lista de endpoints
            description = "Devuelve los detalles de asistencia registrados.")
    // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Lista de detalles de asistencia (vacía si no hay ninguna)"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al obtener los detalles de asistencia")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping
    public ResponseEntity<List<AttendanceDetailDTO>> findAll()throws Exception{
        List<AttendanceDetailDTO> list =mapperUtil.mapList(service.findAll(), AttendanceDetailDTO.class);
        return ResponseEntity.ok(list);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Busca un detalle de asistencia por su id", // título corto que se ve en la lista de endpoints
            description = "Devuelve el detalle de asistencia que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del detalle de asistencia",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El detalle de asistencia encontrado"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "404", description = "No existe el detalle de asistencia con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al buscar detalle de asistencia")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDetailDTO> findById(@PathVariable("id") Integer id)throws Exception{
        AttendanceDetailDTO objDTO=mapperUtil.map(service.findById(id), AttendanceDetailDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Registra un detalle de asistencia nuevo", // título corto que se ve en la lista de endpoints
            description = "Crea un detalle de asistencia nuevo con los datos que se envían en el cuerpo (JSON)." +
                    " Un miembro solo puede registrar asistencia en la célula de la asistencia indicada, " +
                    "y una sola vez por periodo.") // explicación que se ve al abrir el endpoint (cierra el @Operation)
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "201", description = "El detalle de asistencia se creó correctamente"),
            // código 201: se creó el registro
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "500", description = "Regla de negocio no cumplida o error interno." +
                    " El mensaje viene en errorMessage, por ejemplo: «La asistencia indicada no existe.»," +
                    " «El miembro de célula indicado no existe.»," +
                    " «Este miembro no pertenece a la célula de esta asistencia.», " +
                    "«Ya existe un registro de asistencia para este miembro en este periodo.», " +
                    "«Error al crear Dettalle de Asistencia».")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PostMapping
    public ResponseEntity<AttendanceDetailDTO> save(@Valid @RequestBody AttendanceDetailDTO dto)throws Exception{
        AttendanceDetail obj=service.save(mapperUtil.map(dto,AttendanceDetail.class));
        AttendanceDetailDTO objDTO=mapperUtil.map(obj, AttendanceDetailDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Actualiza un detalle de asistencia existente", // título corto que se ve en la lista de endpoints
            description = "Actualiza los datos del detalle de asistencia que tiene el id indicado, " +
                    "con lo que se envía en el cuerpo (JSON).", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del detalle de asistencia",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "El detalle de asistencia se actualizó correctamente"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "400", description = "Faltan datos obligatorios o tienen un formato inválido."),
            // código 400: datos faltantes o con formato inválido
            @ApiResponse(responseCode = "404", description = "No existe el detalle de asistencia con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al actualizar Dettalle de Asistencia")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @PutMapping("/{id}")
    public ResponseEntity <AttendanceDetailDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody AttendanceDetailDTO dto)throws Exception{
        AttendanceDetail obj =service.update(mapperUtil.map(dto, AttendanceDetail.class),id);
        AttendanceDetailDTO objDTO=mapperUtil.map(obj,AttendanceDetailDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @Operation( // documenta este endpoint en Swagger
            summary = "Elimina un detalle de asistencia", // título corto que se ve en la lista de endpoints
            description = "Elimina el detalle de asistencia que tiene el id indicado.",
            // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "Id del detalle de asistencia",
                            example = "1") // parámetro 'id': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "204", description = "El detalle de asistencia se eliminó (no devuelve contenido)"),
            // código 204: se eliminó, sin contenido que devolver
            @ApiResponse(responseCode = "404", description = "No existe el detalle de asistencia con ese id."),
            // código 404: no existe ese id
            @ApiResponse(responseCode = "500", description = "Error al eliminar detalle de asistencia")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Punto 13: reporte de inasistencias consecutivas de un
    // Discipulo en su celula (solo informa, no da de baja a nadie).
    @Operation( // documenta este endpoint en Swagger
            summary = "Cuenta las inasistencias consecutivas de un miembro",
            // título corto que se ve en la lista de endpoints
            description = "Cuenta cuántos periodos seguidos, desde el más reciente hacia atrás, " +
                    "el miembro no asistió. Se corta en cuanto aparece una asistencia. Es un reporte: no da de baja a nadie.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "idMemberCell", in = ParameterIn.PATH, description = "Id del miembro " +
                            "de célula (MemberCell)", example = "1")
                    // parámetro 'idMemberCell': va en la URL (PATH)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Número de inasistencias consecutivas " +
                    "(0 si asistió al último periodo)"), // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al contar las inasistencias consecutivas.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/inasistencias-consecutivas/{idMemberCell}")
    public ResponseEntity<Integer> countConsecutiveAbsences(@PathVariable("idMemberCell") Integer idMemberCell) throws Exception {
        return ResponseEntity.ok(service.countConsecutiveAbsences(idMemberCell));
    }

    // Requerimiento 16: inasistencias de un MemberCell dentro de un
    // rango de fechas -- el frontend calcula el rango (mes,
    // trimestre, semestre, anio) y lo manda como start/end.
    @Operation( // documenta este endpoint en Swagger
            summary = "Cuenta las inasistencias de un miembro en un rango de fechas",
            // título corto que se ve en la lista de endpoints
            description = "Cuenta todas las inasistencias del miembro dentro del rango, " +
                    "sin cortarse en la primera asistencia. El rango se compara contra la fecha de inicio del periodo. Es un reporte: no da de baja a nadie.", // explicación que se ve al abrir el endpoint
            parameters = { // explica los parámetros del endpoint
                    @Parameter(name = "idMemberCell", in = ParameterIn.QUERY, description = "Id del miembro de célula " +
                            "(MemberCell)", example = "1"), // parámetro 'idMemberCell': va después del ? en la URL (QUERY)
                    @Parameter(name = "start", in = ParameterIn.QUERY, description = "Inicio del rango, formato ISO",
                            example = "2026-01-01T00:00:00"), // parámetro 'start': va después del ? en la URL (QUERY)
                    @Parameter(name = "end", in = ParameterIn.QUERY, description = "Fin del rango, formato ISO",
                            example = "2026-03-31T23:59:59") // parámetro 'end': va después del ? en la URL (QUERY)
            }) // cierra la lista de parámetros y el @Operation
    @ApiResponses({ // respuestas posibles del endpoint
            @ApiResponse(responseCode = "200", description = "Total de inasistencias del rango (0 si no tiene)"),
            // código 200: todo salió bien
            @ApiResponse(responseCode = "500", description = "Error al contar las inasistencias del rango indicado.")
            // código 500: regla de negocio no cumplida o error interno
    }) // cierra la lista de respuestas
    @GetMapping("/inasistencias") // GET /attendancedetail/inasistencias
    public ResponseEntity<Long> countAbsencesByDateRange(
            // devuelve el total de inasistencias
            @RequestParam("idMemberCell") Integer idMemberCell, // el MemberCell a consultar
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            // inicio del rango, en formato ISO
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws Exception { // fin del rango, en formato ISO
        return ResponseEntity.ok(service.countAbsencesByDateRange(idMemberCell, start, end));
        // delega en el service y responde 200
    } // cierra el endpoint


}

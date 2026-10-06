package com.ponc.controller;

import com.ponc.dto.AttendanceDetailDTO;
import com.ponc.model.AttendanceDetail;
import com.ponc.service.IAttendanceDetailService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/attendancedetail")
@AllArgsConstructor
public class AttendanceDetailController {

    private final IAttendanceDetailService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<AttendanceDetailDTO>> findAll()throws Exception{
        List<AttendanceDetailDTO> list =mapperUtil.mapList(service.findAll(), AttendanceDetailDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDetailDTO> findById(@PathVariable("id") Integer id)throws Exception{
        AttendanceDetailDTO objDTO=mapperUtil.map(service.findById(id), AttendanceDetailDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @PostMapping
    public ResponseEntity<AttendanceDetailDTO> save(@Valid @RequestBody AttendanceDetailDTO dto)throws Exception{
        AttendanceDetail obj=service.save(mapperUtil.map(dto,AttendanceDetail.class));
        AttendanceDetailDTO objDTO=mapperUtil.map(obj, AttendanceDetailDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity <AttendanceDetailDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody AttendanceDetailDTO dto)throws Exception{
        AttendanceDetail obj =service.update(mapperUtil.map(dto, AttendanceDetail.class),id);
        AttendanceDetailDTO objDTO=mapperUtil.map(obj,AttendanceDetailDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Punto 13: reporte de inasistencias consecutivas de un
    // Discipulo en su celula (solo informa, no da de baja a nadie).
    @GetMapping("/inasistencias-consecutivas/{idMemberCell}")
    public ResponseEntity<Integer> countConsecutiveAbsences(@PathVariable("idMemberCell") Integer idMemberCell) throws Exception {
        return ResponseEntity.ok(service.countConsecutiveAbsences(idMemberCell));
    }

    // Requerimiento 16: inasistencias de un MemberCell dentro de un
    // rango de fechas -- el frontend calcula el rango (mes,
    // trimestre, semestre, anio) y lo manda como start/end.
    @GetMapping("/inasistencias") // GET /attendancedetail/inasistencias
    public ResponseEntity<Long> countAbsencesByDateRange(
            // devuelve el total de inasistencias
            @RequestParam("idMemberCell") Integer idMemberCell, // el MemberCell a consultar
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, // inicio del rango, en formato ISO
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws Exception { // fin del rango, en formato ISO
        return ResponseEntity.ok(service.countAbsencesByDateRange(idMemberCell, start, end)); // delega en el service y responde 200
    } // cierra el endpoint


}

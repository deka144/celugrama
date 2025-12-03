package com.ponc.controller;

import com.ponc.dto.AttendanceDTO;
import com.ponc.model.Attendance;
import com.ponc.service.IAttendanceService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendances")
@AllArgsConstructor
public class AttendanceController {
//todo ok con Git multiLogin
    private final IAttendanceService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> findAll () throws Exception{
        List<AttendanceDTO> list=mapperUtil.mapList(service.findAll(), AttendanceDTO.class);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> findById(@PathVariable("id") Integer id)throws Exception{
        AttendanceDTO objDTO=mapperUtil.map(service.findById(id), AttendanceDTO.class);
        return ResponseEntity.ok().body(objDTO);
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> save (@Valid @RequestBody AttendanceDTO dto)throws Exception{
        Attendance obj= service.save(mapperUtil.map(dto, Attendance.class));
        AttendanceDTO objDTO=mapperUtil.map(obj, AttendanceDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody AttendanceDTO dto)throws Exception{
        Attendance obj =service.update(mapperUtil.map(dto,Attendance.class),id);
        AttendanceDTO objDTO=mapperUtil.map(obj, AttendanceDTO.class);
        return ResponseEntity.ok().body(objDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

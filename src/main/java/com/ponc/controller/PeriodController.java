package com.ponc.controller;

import com.ponc.dto.PeriodDTO;
import com.ponc.model.Period;
import com.ponc.service.IPeriodService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/periods")
@AllArgsConstructor
public class PeriodController {
    private final IPeriodService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<PeriodDTO>> findAll()throws Exception{
        List<PeriodDTO> list=mapperUtil.mapList(service.findAll(),PeriodDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PeriodDTO> finById(@PathVariable("id") Integer id)throws Exception{
        PeriodDTO objDTO=mapperUtil.map(service.findById(id),PeriodDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<PeriodDTO> save(@Valid @RequestBody PeriodDTO dto)throws Exception{
        Period obj=service.save(mapperUtil.map(dto,Period.class));
        PeriodDTO objDTO=mapperUtil.map(obj,PeriodDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PeriodDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody PeriodDTO dto)throws Exception{
        Period obj = service.update(mapperUtil.map(dto,Period.class),id);
        PeriodDTO objDTO=mapperUtil.map(obj,PeriodDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

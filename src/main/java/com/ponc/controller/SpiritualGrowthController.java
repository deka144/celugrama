package com.ponc.controller;

import com.ponc.dto.SpiritualGrowthDTO;
import com.ponc.dto.SpiritualGrowthPersonDTO;
import com.ponc.model.SpiritualGrowth;
import com.ponc.repo.ISpiritualGrowthRepo;
import com.ponc.service.ISpiritualGrowthService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spiritualgrowths")
@AllArgsConstructor
public class SpiritualGrowthController {
    private final ISpiritualGrowthService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<SpiritualGrowthDTO>> getAllSpiritualGrowth() throws Exception{
        List <SpiritualGrowthDTO> list = mapperUtil.mapList(service.findAll(),SpiritualGrowthDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SpiritualGrowthDTO> findById(@PathVariable("id") Integer id) throws Exception{
        SpiritualGrowthDTO objDTO=mapperUtil.map(service.findById(id),SpiritualGrowthDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<SpiritualGrowthDTO> save(@Valid @RequestBody SpiritualGrowthDTO dto)throws Exception{
        SpiritualGrowth obj=service.save(mapperUtil.map(dto,SpiritualGrowth.class));
        SpiritualGrowthDTO objDTO=mapperUtil.map(obj,SpiritualGrowthDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<SpiritualGrowthDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody SpiritualGrowthDTO dto)throws Exception{
        SpiritualGrowth obj=service.update(mapperUtil.map(dto,SpiritualGrowth.class),id);
        SpiritualGrowthDTO objDTO=mapperUtil.map(obj,SpiritualGrowthDTO.class);
       return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

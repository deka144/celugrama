package com.ponc.controller;

import com.ponc.dto.SpiritualGrowthPersonDTO;
import com.ponc.model.SpiritualGrowthPerson;
import com.ponc.service.ISpiritualGrowthPersonService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spiritualgrowthgersons")
@AllArgsConstructor
public class SpiritualGrowthPersonController {

    private final ISpiritualGrowthPersonService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<SpiritualGrowthPersonDTO>> findAll() throws Exception{
        List<SpiritualGrowthPersonDTO> list = mapperUtil.mapList(service.findAll(), SpiritualGrowthPersonDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpiritualGrowthPersonDTO> findById(@PathVariable("id") Integer id) throws Exception{
        SpiritualGrowthPersonDTO objDTO=mapperUtil.map(service.findById(id),SpiritualGrowthPersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @PostMapping
    public ResponseEntity<SpiritualGrowthPersonDTO> create(@Valid @RequestBody SpiritualGrowthPersonDTO dto) throws Exception{
        SpiritualGrowthPerson obj=service.save(mapperUtil.map(dto,SpiritualGrowthPerson.class));
        SpiritualGrowthPersonDTO objDTO=mapperUtil.map(obj,SpiritualGrowthPersonDTO.class);

        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<SpiritualGrowthPersonDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody SpiritualGrowthPersonDTO dto) throws Exception{

        SpiritualGrowthPerson obj=service.update(mapperUtil.map(dto,SpiritualGrowthPerson.class),id);
        SpiritualGrowthPersonDTO objDTO=mapperUtil.map(obj,SpiritualGrowthPersonDTO.class);

        return ResponseEntity.ok(objDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

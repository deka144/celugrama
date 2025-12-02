package com.ponc.controller;

import com.ponc.dto.CellDTO;
import com.ponc.model.Cell;
import com.ponc.service.ICellService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cells")
@AllArgsConstructor
public class CellController {

    private final ICellService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<CellDTO>> findAll()throws Exception{
        List<CellDTO> list = mapperUtil.mapList(service.findAll(), CellDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CellDTO> findById(@PathVariable("id") Integer id)throws Exception{
        CellDTO objDTO=mapperUtil.map(service.findById(id), CellDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<CellDTO> save(@Valid @RequestBody CellDTO dto)throws Exception{
        Cell obj= service.save(mapperUtil.map(dto,Cell.class));
        CellDTO objDTO=mapperUtil.map(obj,CellDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CellDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody CellDTO dto)throws Exception{
        Cell obj=service.update(mapperUtil.map(dto,Cell.class),id);
        CellDTO objDto=mapperUtil.map(obj,CellDTO.class);
        return ResponseEntity.ok(objDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

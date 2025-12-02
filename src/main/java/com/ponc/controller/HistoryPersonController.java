package com.ponc.controller;

import com.ponc.dto.HistoryPersonDTO;
import com.ponc.model.HistoryPerson;
import com.ponc.service.IHistoryPersonService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historypersons")
@AllArgsConstructor
public class HistoryPersonController {
    private final IHistoryPersonService service;
    private final MapperUtil mapperUtil;


    @GetMapping
    public ResponseEntity<List<HistoryPersonDTO>> findAll()throws Exception{
        List<HistoryPersonDTO> list=mapperUtil.mapList(service.findAll(),HistoryPersonDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HistoryPersonDTO> findById(@PathVariable("id") Integer id )throws Exception{
        HistoryPersonDTO objDTO=mapperUtil.map(service.findById(id),HistoryPersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<HistoryPersonDTO> save (@Valid @RequestBody HistoryPersonDTO dto)throws Exception{
        HistoryPerson obj=service.save(mapperUtil.map(dto,HistoryPerson.class));
        HistoryPersonDTO objDTO=mapperUtil.map(obj, HistoryPersonDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<HistoryPersonDTO> update (@Valid @PathVariable("id") Integer id,@RequestBody HistoryPersonDTO dto )throws Exception{
        HistoryPerson obj=service.update(mapperUtil.map(dto,HistoryPerson.class),id);
        HistoryPersonDTO objDTO=mapperUtil.map(obj,HistoryPersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

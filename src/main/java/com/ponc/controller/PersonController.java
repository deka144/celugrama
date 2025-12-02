package com.ponc.controller;

import com.ponc.dto.PersonDTO;
import com.ponc.model.Person;
import com.ponc.service.IPersonService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private final IPersonService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll()throws Exception{
        List<PersonDTO> list =mapperUtil.mapList(service.findAll(), PersonDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Integer id)throws Exception{
        PersonDTO objDTO= mapperUtil.map(service.findById(id), PersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<PersonDTO> save (@Valid @RequestBody PersonDTO dto)throws Exception{
        Person obj=service.save(mapperUtil.map(dto,Person.class));
        PersonDTO objDTO=mapperUtil.map(obj,PersonDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PersonDTO dto)throws Exception{
        Person obj = service.update(mapperUtil.map(dto,Person.class),id);
        PersonDTO objDTO=mapperUtil.map(obj,PersonDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

package com.ponc.controller;

import com.ponc.dto.GuestDTO;
import com.ponc.model.Guest;
import com.ponc.service.IGuestService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Guests")
@AllArgsConstructor
public class GuestController {
    private final IGuestService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<GuestDTO>> findAll()throws Exception{
        List<GuestDTO> list =mapperUtil.mapList(service.findAll(),GuestDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> findById(@PathVariable("id") Integer id)throws Exception{
        GuestDTO objDTO=mapperUtil.map(service.findById(id),GuestDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<GuestDTO> save (@Valid @RequestBody GuestDTO dto)throws Exception{
        Guest obj=service.save(mapperUtil.map(dto, Guest.class));
        GuestDTO objDTO=mapperUtil.map(obj, GuestDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> update (@Valid @PathVariable("id") Integer id, @RequestBody GuestDTO dto)throws Exception{
        Guest obj=service.update(mapperUtil.map(dto, Guest.class),id);
        GuestDTO objDTO=mapperUtil.map(obj,GuestDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

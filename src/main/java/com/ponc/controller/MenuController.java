package com.ponc.controller;

import com.ponc.dto.MenuDTO;
import com.ponc.model.Menu;
import com.ponc.service.IMenuService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
@AllArgsConstructor
public class MenuController {
    private final IMenuService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<MenuDTO>> findAll()throws Exception{
        List<MenuDTO> list =mapperUtil.mapList(service.findAll(),MenuDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> findById(@PathVariable("id") Integer id)throws Exception{
        MenuDTO objDTO=mapperUtil.map(service.findById(id),MenuDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<MenuDTO> save(@Valid @RequestBody MenuDTO dto)throws Exception{
        Menu obj=service.save(mapperUtil.map(dto,Menu.class));
        MenuDTO objDTO=mapperUtil.map(obj,MenuDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody MenuDTO dto)throws Exception{
        Menu obj =service.update(mapperUtil.map(dto,Menu.class),id);
        MenuDTO objDTO=mapperUtil.map(obj,MenuDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id )throws Exception {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

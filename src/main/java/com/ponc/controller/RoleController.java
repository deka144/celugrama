package com.ponc.controller;

import com.ponc.dto.RoleDTO;
import com.ponc.model.Role;
import com.ponc.service.IRoleService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private final IRoleService service;
    private final MapperUtil mapperUtil;


    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() throws Exception{
        List<RoleDTO> list = mapperUtil.mapList(service.findAll(), RoleDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable("id") Integer id )throws Exception{
        Role obj = service.findById(id);
        RoleDTO objDto =mapperUtil.map(obj, RoleDTO.class);
        return ResponseEntity.ok(objDto);
    }

    @PostMapping
    public ResponseEntity<RoleDTO>  save(@Valid @RequestBody RoleDTO dto)throws Exception{
        Role obj = service.save(mapperUtil.map(dto, Role.class));
        RoleDTO objDTO= mapperUtil.map(obj,RoleDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update (@Valid @PathVariable("id") Integer id,@RequestBody RoleDTO dto)throws  Exception{
        Role obj = service.update(mapperUtil.map(dto, Role.class),id);
        RoleDTO objDTO= mapperUtil.map(obj, RoleDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id )throws  Exception{
            service.deleteById(id);
            return ResponseEntity.noContent().build();
    }
}

package com.ponc.controller;

import com.ponc.dto.UserDTO;
import com.ponc.model.User;
import com.ponc.service.IUserService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() throws Exception{
        List<UserDTO> list=mapperUtil.mapList(service.findAll(),UserDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Integer id) throws Exception{
        UserDTO objDTO=mapperUtil.map(service.findById(id),UserDTO.class);
        return ResponseEntity.ok(objDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO dto) throws Exception{

        User obj=service.save(mapperUtil.map(dto,User.class));
        UserDTO objDTO=mapperUtil.map(obj,UserDTO.class);

        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@Valid @PathVariable("id") Integer id,@RequestBody UserDTO dto) throws Exception{
        User obj=service.update(mapperUtil.map(dto,User.class),id);
        UserDTO objDTO=mapperUtil.map(obj,UserDTO.class);

        return ResponseEntity.ok(objDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

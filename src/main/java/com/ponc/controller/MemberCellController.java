package com.ponc.controller;

import com.ponc.dto.MemberCellDTO;
import com.ponc.model.MemberCell;
import com.ponc.service.IMemberCellService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membercells")
@AllArgsConstructor
public class MemberCellController {
    private final IMemberCellService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<MemberCellDTO>> findAll()throws Exception{
        List<MemberCellDTO> list = mapperUtil.mapList(service.findAll(),MemberCellDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MemberCellDTO> findById(@PathVariable("id") Integer id)throws Exception{
        MemberCellDTO objDTO= mapperUtil.map(service.findById(id),MemberCellDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<MemberCellDTO> save(@Valid @RequestBody MemberCellDTO dto)throws Exception{
        MemberCell obj =service.save(mapperUtil.map(dto, MemberCell.class));
        MemberCellDTO objDTO=mapperUtil.map(obj,MemberCellDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MemberCellDTO> update(@Valid @PathVariable("id")Integer id, @RequestBody MemberCellDTO dto)throws Exception{
        MemberCell obj=service.update(mapperUtil.map(dto, MemberCell.class),id);
        MemberCellDTO objDTO=mapperUtil.map(obj, MemberCellDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

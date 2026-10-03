package com.ponc.controller;

import com.ponc.dto.ChangeCellDTO;
import com.ponc.dto.ChangeCellLeaderRequestDTO;
import com.ponc.dto.ChangeCellRequestDTO;
import com.ponc.model.ChangeCell;
import com.ponc.service.IChangeCellService;
import com.ponc.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ChangeCells")
@AllArgsConstructor
public class ChangeCellController {
    private final IChangeCellService service;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<ChangeCellDTO>> findAll()throws Exception{
        List<ChangeCellDTO> list = mapperUtil.mapList(service.findAll(), ChangeCellDTO.class);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChangeCellDTO> findById(@PathVariable("id") Integer id)throws Exception{
        ChangeCellDTO objDTO=mapperUtil.map(service.findById(id),ChangeCellDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    public ResponseEntity<ChangeCellDTO> save(@Valid @RequestBody ChangeCellDTO dto )throws Exception{
        ChangeCell obj=service.save(mapperUtil.map(dto, ChangeCell.class));
        ChangeCellDTO objDTO=mapperUtil.map(obj,ChangeCellDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);

    }

    // Punto 8: nuevo endpoint que orquesta el cambio de celula completo
    // (desactiva la vieja, crea la nueva, crea el ChangeCell y el
    // HistoryPerson), en vez de armar el ChangeCell a mano con el
    // POST /ChangeCells de arriba.
    @PostMapping("/cambiar-celula")
    public ResponseEntity<ChangeCellDTO> changeCell(@Valid @RequestBody ChangeCellRequestDTO dto) throws Exception {
        ChangeCell obj = service.changeCell(dto.getIdMemberCellOld(), dto.getIdCellNew());
        ChangeCellDTO objDTO = mapperUtil.map(obj, ChangeCellDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }

    // Punto 12: nuevo endpoint que orquesta el cambio de Lider de una
    // celula completo (desactiva el viejo, crea el nuevo, crea el
    // ChangeCell y el HistoryPerson).
    @PostMapping("/cambiar-lider")
    public ResponseEntity<ChangeCellDTO> changeCellLeader(@Valid @RequestBody ChangeCellLeaderRequestDTO dto) throws Exception {
        ChangeCell obj = service.changeCellLeader(dto.getIdMemberCellOldLeader(), dto.getIdPersonNewLeader());
        ChangeCellDTO objDTO = mapperUtil.map(obj, ChangeCellDTO.class);
        return new ResponseEntity<>(objDTO, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ChangeCellDTO> update (@Valid @PathVariable("id") Integer id, @RequestBody ChangeCellDTO dto )throws Exception{
        ChangeCell obj=service.update(mapperUtil.map(dto, ChangeCell.class),id);
        ChangeCellDTO objDTO=mapperUtil.map(obj, ChangeCellDTO.class);
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Integer id)throws Exception{
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

package com.ponc.dto;

import com.ponc.model.enums.CellType;
import com.ponc.model.enums.DayType;
import com.ponc.model.enums.Grid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellResponseDTO {

    private Integer idCell;

    private String level;

    private String address;

    private String reference;

    private String cellular;

    private String phone;

    private String photoUrl;

    private LocalDateTime openDate;

    private DayType day; // mismo tipo que la entidad Cell, para que ModelMapper copie el valor directo

    private String hour;

    private Grid grid;

    private CellType type;

    private String observations;

    private Boolean state; // estado: true = activo, false = inactivo (sin valor inicial: si el JSON no lo trae, queda vacío)

}

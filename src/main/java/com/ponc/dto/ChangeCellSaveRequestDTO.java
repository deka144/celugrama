package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// No lleva idChangeCell. Se llama ChangeCellSaveRequestDTO porque ChangeCellRequestDTO ya existe (es el cuerpo de /cambiar-celula).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class ChangeCellSaveRequestDTO { // cuerpo del POST y del PUT de Cambio de célula

    @NotNull
    private Integer idMemberCellOld; // miembro anterior

    @NotNull
    private Integer idMemberCellNew; // miembro nuevo

    @NotNull
    private LocalDateTime changeDate; // fecha del cambio
}

package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCellRequestDTO {

    @NotNull
    private Integer idMemberCellOld;

    @NotNull
    private Integer idCellNew;
}

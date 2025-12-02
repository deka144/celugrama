package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCellDTO {

    private Integer idChangeCell;

    @NotNull
    private Integer idMemberCellOld;
    @NotNull
    private Integer idMemberCellNew;
    @NotNull
    private LocalDateTime changeDate;
}

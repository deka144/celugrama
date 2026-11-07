package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCellResponseDTO {

    private Integer idChangeCell;

    private Integer idMemberCellOld;

    private Integer idMemberCellNew;

    private LocalDateTime changeDate;
}

package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPersonResponseDTO {
    private Integer idHistoryPerson;

    private String nameTable;

    private Integer IdTable;
}

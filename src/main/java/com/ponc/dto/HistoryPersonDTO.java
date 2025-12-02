package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPersonDTO {
    private Integer idHistoryPerson;

    @NotNull
    private String nameTable;

    @NotNull
    private Integer IdTable;
}

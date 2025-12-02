package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO {

    private Integer idGuest;

    @NotNull
    private Integer idPeriod;

    @NotNull
    private Integer idCell;

    @NotNull
    private Integer idPerson;
}

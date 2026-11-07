package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestResponseDTO {

    private Integer idGuest;

    private Integer idPeriod;

    private Integer idCell;

    private Integer idPerson;
}

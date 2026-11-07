package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpiritualGrowthResponseDTO {

    private Integer idSpiritualGrowth;

    private String description;

    private Boolean state;

}

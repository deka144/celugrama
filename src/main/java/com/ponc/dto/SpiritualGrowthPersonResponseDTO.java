package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpiritualGrowthPersonResponseDTO {

    private Integer idSpiritualGrowthPerson;

    private Integer idPerson;

    private Integer idSpiritualGrowth ;

    private LocalDateTime date;

    private String Periodo;
}

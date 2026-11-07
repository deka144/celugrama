package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodResponseDTO {

    private Integer idPeriod;

    private Integer week;

    private String monthtxt;

    private Integer months;

    private Integer years;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String theme1;

    private String theme2;
}

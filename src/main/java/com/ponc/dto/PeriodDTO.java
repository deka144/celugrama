package com.ponc.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodDTO {

    private Integer idPeriod;

    @NotNull
    private Integer week;

    @NotNull
    private String monthtxt;

    @NotNull
    private Integer months;

    @NotNull
    private Integer years;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private String theme1;

    @NotNull
    private String theme2;
}

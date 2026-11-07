package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// No lleva idPeriod (lo genera la base de datos).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class PeriodRequestDTO { // cuerpo del POST y del PUT de Período

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

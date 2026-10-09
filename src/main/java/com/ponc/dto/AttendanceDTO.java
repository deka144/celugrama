package com.ponc.dto;

import com.ponc.model.AttendanceDetail;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {
    private Integer idAttendance;

    @NotNull
    private Integer idCell;

    @NotNull
    private Integer idPeriod;

    @NotNull
    private BigDecimal offering;
    // ANTES: Integer. Ahora BigDecimal: no pierde los 2 decimales del monto (200.50 ya no se corta a 200)

    List<AttendanceDetailDTO> attendanceDetails;
    // ANTES: List<AttendanceDetail> (la entidad, que se repetía sin fin al convertir a JSON).
    // AttendanceDetailDTO está en este mismo paquete: no hace falta import

}

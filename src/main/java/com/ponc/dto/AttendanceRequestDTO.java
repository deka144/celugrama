package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// No lleva idAttendance (lo genera la base de datos) ni la lista attendanceDetails (cada detalle se registra por /attendancedetail).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class AttendanceRequestDTO { // cuerpo del POST y del PUT de Asistencia

    @NotNull
    private Integer idCell; // célula a la que pertenece la asistencia

    @NotNull
    private Integer idPeriod; // período de la asistencia

    @NotNull
    private BigDecimal offering; // ofrenda recogida (conserva los 2 decimales)
}


package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// No lleva idAttendanceDetail (lo genera la base de datos).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class AttendanceDetailRequestDTO { // cuerpo del POST y del PUT de Detalle de asistencia

    @NotNull
    private Integer idAttendance; // asistencia a la que pertenece el detalle

    @NotNull
    private Integer idMemberCell; // miembro de célula al que se le marca la asistencia

    private Boolean attendance=false; // true = asistió, false = faltó (valor inicial false)
}


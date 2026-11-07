package com.ponc.dto;

import com.ponc.model.enums.CellType;
import com.ponc.model.enums.DayType;
import com.ponc.model.enums.Grid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// No lleva idCell (viene en la URL en el PUT y lo genera la base de datos en el POST) ni state (el servidor lo decide al crear y solo cambia con PATCH /{id}/state).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class CellRequestDTO { // cuerpo del POST y del PUT de Célula

    @NotNull
    private String level;

    @NotNull
    private String address;

    @NotNull
    private String reference;

    @NotNull
    private String cellular;

    @NotNull
    private String phone;

    @NotNull
    private String photoUrl;

    @NotNull
    private LocalDateTime openDate;

    @NotNull
    private DayType day; // LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO o DOMINGO

    @NotNull
    private String hour;

    @NotNull
    private Grid grid;

    @NotNull
    private CellType type; // el tipo decide el state inicial: DISCIPULADO nace inactiva, las demás activas

    private String observations; // opcional
}

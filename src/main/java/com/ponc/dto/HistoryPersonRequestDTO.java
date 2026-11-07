package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// No lleva idHistoryPerson (lo genera la base de datos).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class HistoryPersonRequestDTO { // cuerpo del POST y del PUT de Historial de persona

    @NotNull
    private String nameTable; // nombre de la tabla afectada

    @NotNull
    private Integer idTable; // id del registro afectado (en el JSON sigue llamándose idTable)
}

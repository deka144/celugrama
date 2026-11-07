package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// No lleva idGuest (lo genera la base de datos).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class GuestRequestDTO { // cuerpo del POST y del PUT de Invitado

    @NotNull
    private Integer idPeriod;

    @NotNull
    private Integer idCell;

    @NotNull
    private Integer idPerson;
}


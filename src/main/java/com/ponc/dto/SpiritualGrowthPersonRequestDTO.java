package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// No lleva idSpiritualGrowthPerson (ahora lo genera la base de datos).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class SpiritualGrowthPersonRequestDTO { // cuerpo del POST y del PUT de Crecimiento espiritual de una persona

    @NotNull
    private Integer idPerson;

    @NotNull
    private Integer idSpiritualGrowth;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private String periodo; // en el JSON sigue llamándose periodo
}


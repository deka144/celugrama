package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// No lleva idSpiritualGrowth ni state (nace activo y solo cambia con PATCH /{id}/state).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class SpiritualGrowthRequestDTO { // cuerpo del POST y del PUT de Crecimiento espiritual

    @NotNull
    private String description;
}


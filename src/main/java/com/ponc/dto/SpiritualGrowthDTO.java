package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpiritualGrowthDTO {

    private Integer idSpiritualGrowth;

    @NotNull
    private String description;

    //private Boolean state=true;

    // BORRADO: private Boolean state=true;
    @NotNull(groups = OnUpdate.class) // obligatorio solo al editar (PUT); al crear (POST) no se pide, porque el servidor lo pone en true
    private Boolean state; // estado: true = activo, false = inactivo (sin valor inicial: si el JSON no lo trae, queda vacío)

}

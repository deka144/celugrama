package com.ponc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // crea getState() y setState()
@AllArgsConstructor // constructor con el campo state
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON
public class StateDTO {// cuerpo que reciben todos los métodos PATCH /{id}/state

    @NotNull // si el JSON no trae state, responde 400
    @Schema(description = "Nuevo estado: true = activo, false = inactivo", example = "false") // texto y ejemplo que se ven en Swagger
    private Boolean state; // el nuevo valor que se quiere guardar

}

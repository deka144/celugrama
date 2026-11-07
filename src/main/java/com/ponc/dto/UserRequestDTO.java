package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// No lleva idUser (ahora lo genera la base de datos) ni state (nace activo y solo cambia con PATCH /{id}/state).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class UserRequestDTO { // cuerpo del POST y del PUT de Usuario

    @NotNull
    private Integer idPerson; // persona dueña del usuario

    @NotNull
    private String username;

    @NotNull
    private String password; // solo se recibe; las respuestas (UserDTO) ya no la devuelven
}


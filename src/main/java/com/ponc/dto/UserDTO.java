package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer idUser;

    @NotNull
    private Integer idPerson;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean state=true;
}

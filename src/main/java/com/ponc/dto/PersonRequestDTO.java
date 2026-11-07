package com.ponc.dto;

import com.ponc.model.enums.CivilStatus;
import com.ponc.model.enums.DocumentType;
import com.ponc.model.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// No lleva idPerson ni state (nace activa y solo cambia con PATCH /{id}/state).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class PersonRequestDTO { // cuerpo del POST y del PUT de Persona

    @NotNull
    private String name;

    @NotNull
    private String paternalSurname;

    @NotNull
    private String maternalSurname;

    @NotNull
    private LocalDateTime birthDate;

    @NotNull
    private Gender genre;

    @NotNull
    private String cellular;

    private String phone; // opcional

    @NotNull
    private String address;

    @NotNull
    private String reference;

    @NotNull
    private String email;

    private String photoUrl; // opcional

    @NotNull
    private DocumentType typeDoc;

    @NotNull
    private String numberDoc;

    @NotNull
    private CivilStatus civilStatus;
}


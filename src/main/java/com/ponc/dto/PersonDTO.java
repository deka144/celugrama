package com.ponc.dto;

import com.ponc.model.enums.CivilStatus;
import com.ponc.model.enums.DocumentType;
import com.ponc.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Integer idPerson;

    @NotNull
    private String name;

    @NotNull
    private String paternalSurname;

    @NotNull
    private String maternalSurname;

    @NotNull
    private LocalDateTime birthDate;

//    @NotNull
//    private String genre;

    // Mismo tipo que en la entidad: asi ModelMapper copia el
    // valor directo, sin configurar ningun conversor.
    @NotNull
    private Gender genre;

    @NotNull
    private String cellular;

    private String phone;

    @NotNull
    private String address;

    @NotNull
    private String reference;

    @NotNull
    private String email;


    private String photoUrl;

//    @NotNull
//    private String typeDoc;

    // Mismo tipo que en la entidad (Paso 2 / Archivo 7)
    @NotNull
    private DocumentType typeDoc;

    @NotNull
    private String numberDoc;

//    @NotNull
//    private String civilStatus;

    // Mismo tipo que en la entidad (Paso 2 / Archivo 7)
    @NotNull
    private CivilStatus civilStatus;

}

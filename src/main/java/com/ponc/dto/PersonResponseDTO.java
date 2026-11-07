package com.ponc.dto;

import com.ponc.model.enums.CivilStatus;
import com.ponc.model.enums.DocumentType;
import com.ponc.model.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDTO {

    private Integer idPerson;


    private String name;


    private String paternalSurname;


    private String maternalSurname;


    private LocalDateTime birthDate;


    private Gender genre;


    private String cellular;

    private String phone;


    private String address;


    private String reference;


    private String email;

    private String photoUrl;

    private DocumentType typeDoc;


    private String numberDoc;


    private CivilStatus civilStatus;

    private Boolean state;

}

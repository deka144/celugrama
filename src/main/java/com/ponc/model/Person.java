package com.ponc.model;

import com.ponc.model.enums.CivilStatus;
import com.ponc.model.enums.DocumentType;
import com.ponc.model.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data //le crea getter, setter, equals, hashcode y tostring //de Lombok
@AllArgsConstructor //constructor con todos los parametros //de Lombok
@NoArgsConstructor // contructor sin parametros //de Lombok
@Entity //Indica que esta clase sera una tabla en la DB //de JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idPerson;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String paternalSurname;

    @Column(nullable=false)
    private String maternalSurname;

    @Column(nullable=false)
    private LocalDateTime birthDate;

//    @Column(nullable=false, length=1)
//    private String genre;

    // Antes: private String genre; con length=1 ('M'/'F')
    @Enumerated(EnumType.STRING) // guarda el NOMBRE del enum
    @Column(nullable=false, length=20) // 20 alcanza de sobra
    private Gender genre;


    @Column(nullable=false, length=12)
    private String cellular;

    @Column(nullable=true, length=12)
    private String phone;

    @Column(nullable=false)
    private String address;

    @Column(nullable=false)
    private String reference;

    @Column(nullable=false)
    private String email;

    @Column(nullable=true)
    private String photoUrl;

//    @Column(nullable=false, length=1)
//    private String typeDoc;

    // Antes: private String typeDoc; con length=1 ('1'..'4')
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private DocumentType typeDoc;


    @Column(nullable=false, length=10, unique = true)
    private String numberDoc;

//    @Column(nullable=false, length=1)
//    private String civilStatus;

    // Antes: private String civilStatus; con length=1
    // ('c', 's', 'v', 'd', 'u')
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private CivilStatus civilStatus;


}

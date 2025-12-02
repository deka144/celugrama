package com.ponc.model;

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

    @Column(nullable=false, length=1)
    private String genre;

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

    @Column(nullable=false, length=1)
    private String typeDoc;

    @Column(nullable=false, length=10, unique = true)
    private String numberDoc;

    @Column(nullable=false, length=1)
    private String civilStatus;

}

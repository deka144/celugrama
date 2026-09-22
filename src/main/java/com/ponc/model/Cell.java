package com.ponc.model;

import com.ponc.model.enums.CellType;
import com.ponc.model.enums.Grid;
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
public class Cell {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCell;

    @Column(nullable=false)
    private String level;

    @Column(nullable=false)
    private String address;

    @Column(nullable=false)
    private String reference;

    @Column(nullable=false, length=12)
    private String cellular;

    @Column(nullable=true, length=12)
    private String phone;

    @Column(nullable=false)
    private String photoUrl;

    @Column(nullable=false)
    private LocalDateTime openDate;

    @Column(nullable=false)
    private String day;

    @Column(nullable=false)
    private String hour;

//    @Column(nullable=false, length=1)
//    private String grid;

    // Antes: private String grid; con length=1 ('n','v','d'...)
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private Grid grid;

//    @Column(nullable=false, length=1)
//    private String type;

    // Antes: private String type; con length=1 ('e'/'d')
    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private CellType type;


    @Column(nullable=true)
    private String observations;

    @Column(nullable=false)
    private Boolean state;
}

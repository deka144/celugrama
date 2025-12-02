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
public class HistoryPerson {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idHistoryPerson;

    @Column
    private String nameTable;

    @Column
    private Integer idTable;

    @Column(nullable=false)
    private LocalDateTime registerDate;
}

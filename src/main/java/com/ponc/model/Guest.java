package com.ponc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data //le crea getter, setter, equals, hashcode y tostring //de Lombok
@AllArgsConstructor //constructor con todos los parametros //de Lombok
@NoArgsConstructor // contructor sin parametros //de Lombok
@Entity //Indica que esta clase sera una tabla en la DB //de JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Guest {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idGuest;

    @ManyToOne
    @EqualsAndHashCode.Include
    @JoinColumn(name="id_period", nullable=false, foreignKey = @ForeignKey(name="FK_GUEST_PERIOD"))
    private Period period;

    @ManyToOne
    @JoinColumn(name="id_cell", nullable=false, foreignKey = @ForeignKey(name="FK_GUEST_CELL"))
    private Cell cell;

    @ManyToOne
    @EqualsAndHashCode.Include
    @JoinColumn(name="id_person", nullable=false, foreignKey = @ForeignKey(name="FK_GUEST_PERSON"))
    private Person person;
}

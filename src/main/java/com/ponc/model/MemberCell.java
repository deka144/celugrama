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
public class MemberCell {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMemberCell;

    @ManyToOne
    @JoinColumn(name="id_cell", nullable=false, foreignKey = @ForeignKey(name="FK_MEMBERCELL_CELL"))
    private Cell cell;


    @ManyToOne
    @JoinColumn(name="id_person", nullable=false, foreignKey = @ForeignKey(name="FK_MEMBERCELL_PERSON"))
    private Person person;

    @Column
    private String typeMemberCell;

    @Column(nullable=false)
    private Boolean state;
}

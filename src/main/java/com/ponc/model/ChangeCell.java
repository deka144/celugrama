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
public class ChangeCell {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idChangeCell;

    @ManyToOne
    @JoinColumn(name="id_member_cell_Old", nullable=false, foreignKey = @ForeignKey(name="FK_CHANGECELLOLD_CELLMEMBER"))
    private MemberCell memberCellOld;

    @ManyToOne
    @JoinColumn(name="id_member_cell_new", nullable=false, foreignKey = @ForeignKey(name="FK_CHANGECELLNEW_CELLMEMBER"))
    private MemberCell memberCellNew;


    @Column(nullable=false)
    private LocalDateTime changeDate;
}

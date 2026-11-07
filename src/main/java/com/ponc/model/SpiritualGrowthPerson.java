package com.ponc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SpiritualGrowthPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // NUEVO: la base de datos genera el id (ya no se envía en el JSON)

    @EqualsAndHashCode.Include
    private Integer idSpiritualGrowthPerson;

    @ManyToOne
    @JoinColumn(name = "id_person", nullable = false, foreignKey = @ForeignKey(name = "FK_SPIRITUALGROWTHPERSON_PERSON"))
    private Person person;

    @ManyToOne
    @JoinColumn(name = "id_spiritualGrowth", nullable = false, foreignKey = @ForeignKey(name = "FK_SPIRITUALGROWTHPERSON_SPIRITUALGROWTH"))
    private SpiritualGrowth spiritualGrowth;

    @Column(nullable=false)
    private LocalDateTime date;

    @Column(nullable=false)
    private String periodo;
}

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
    @EqualsAndHashCode.Include
    private Integer idSpiritualGrowthPerson;

    @ManyToOne
    @JoinColumn(name = "id_person", nullable = false, foreignKey = @ForeignKey(name = "FK_SPIRITUALGROWTHPERSON_PERSON"))
    private Person person;

    @ManyToOne
    @JoinColumn(name = "id_spiritualGrowth", nullable = false, foreignKey = @ForeignKey(name = "FK_SPIRITUALGROWTHPERSON_SPIRITUALGROWTH"))
    private SpiritualGrowth spiritualGrowth;

    @Column
    private LocalDateTime date;

    @Column
    private String periodo;
}

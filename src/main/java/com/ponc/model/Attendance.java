package com.ponc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //le crea getter, setter, equals, hashcode y tostring //de Lombok
@AllArgsConstructor //constructor con todos los parametros //de Lombok
@NoArgsConstructor // contructor sin parametros //de Lombok
@Entity //Indica que esta clase sera una tabla en la DB //de JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idAttendance;

    @ManyToOne
    @JoinColumn(name="id_cel", nullable=false, foreignKey = @ForeignKey(name="FK_ATTENDANCE_CELL"))
    private Cell cell;

    @ManyToOne
    @JoinColumn(name="id_period", nullable=false, foreignKey = @ForeignKey(name="FK_ATTENDANCE_PERIOD"))
    private Period period;

    @Column(nullable=false)
    private String offering;

    @OneToMany(mappedBy="attendance", cascade= {CascadeType.ALL}, orphanRemoval=true )
    List<AttendanceDetail> details;
}

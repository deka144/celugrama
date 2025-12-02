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
public class AttendanceDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idAttendanceDetail;

    @ManyToOne
    @JoinColumn(name="id_atten", nullable=false, foreignKey = @ForeignKey(name="FK_ATTENDETAIL_ATTEN"))
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name="id_MemberCell", nullable=false, foreignKey = @ForeignKey(name="FK_ATTENDETAIL_MEMBERCELL"))
    private MemberCell MemberCell;

    @Column(nullable=false)
    private Boolean attended;
}

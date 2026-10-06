package com.ponc.repo;

import com.ponc.model.AttendanceDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IAttendanceDetailRepo extends IGenericRepo<AttendanceDetail,Integer> {


    // Punto 5: la asistencia se registra una sola vez por periodo, por discipulo.
    boolean existsByAttendance_IdAttendanceAndMemberCell_IdMemberCell(Integer idAttendance, Integer idMemberCell);

    // Punto 13: historial de asistencia de un Discipulo en su celula,
    // del periodo mas reciente al mas antiguo -- para contar las
    // inasistencias consecutivas desde el periodo actual hacia atras.
    List<AttendanceDetail> findByMemberCell_IdMemberCellOrderByAttendance_Period_StartDateDesc(Integer idMemberCell);

    // Requerimiento 16: cuenta inasistencias de un MemberCell dentro
    // de un rango de fechas, sin cortarse en la primera asistencia
    // (a diferencia del reporte de racha del Punto 13). El rango se
    // compara contra el startDate del Period de cada Attendance.
    @Query("SELECT COUNT(ad) FROM AttendanceDetail ad " + // cuenta los registros de asistencia (AttendanceDetail)
            "WHERE ad.memberCell.idMemberCell = :idMemberCell " + // solo de este MemberCell
            "AND ad.attended = false " + // solo las inasistencias (attended = false)
            "AND ad.attendance.period.startDate BETWEEN :start AND :end") // dentro del rango, segun la fecha de inicio del Period
    Long countAbsencesByMemberCellAndDateRange( // devuelve el total (Long, como los otros count)
                                                @Param("idMemberCell") Integer idMemberCell, // el MemberCell a consultar
                                                @Param("start") LocalDateTime start, // inicio del rango
                                                @Param("end") LocalDateTime end); // fin del rango


}

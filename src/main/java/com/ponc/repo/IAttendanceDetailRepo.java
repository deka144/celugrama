package com.ponc.repo;

import com.ponc.model.AttendanceDetail;

import java.util.List;

public interface IAttendanceDetailRepo extends IGenericRepo<AttendanceDetail,Integer> {


    // Punto 5: la asistencia se registra una sola vez por periodo, por discipulo.
    boolean existsByAttendance_IdAttendanceAndMemberCell_IdMemberCell(Integer idAttendance, Integer idMemberCell);

    // Punto 13: historial de asistencia de un Discipulo en su celula,
    // del periodo mas reciente al mas antiguo -- para contar las
    // inasistencias consecutivas desde el periodo actual hacia atras.
    List<AttendanceDetail> findByMemberCell_IdMemberCellOrderByAttendance_Period_StartDateDesc(Integer idMemberCell);

}

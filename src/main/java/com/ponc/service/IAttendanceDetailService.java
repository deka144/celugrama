package com.ponc.service;

import com.ponc.model.AttendanceDetail;

import java.time.LocalDateTime;

public interface IAttendanceDetailService extends ICRUD<AttendanceDetail,Integer>{

    // Punto 13: cuenta las inasistencias consecutivas de un Discipulo
    // en su celula (reporte sin efectos secundarios; la baja sigue
    // siendo una decision manual).
    Integer countConsecutiveAbsences(Integer idMemberCell);

    // Requerimiento 16: cuenta todas las inasistencias de un
    // MemberCell dentro de un rango de fechas (sin cortarse en la
    // primera asistencia). El llamador decide el rango (mes,
    // trimestre, semestre, anio).
    Long countAbsencesByDateRange(Integer idMemberCell, LocalDateTime start, LocalDateTime end); // devuelve el total de inasistencias del rango


}

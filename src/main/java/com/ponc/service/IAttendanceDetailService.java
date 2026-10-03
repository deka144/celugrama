package com.ponc.service;

import com.ponc.model.AttendanceDetail;

public interface IAttendanceDetailService extends ICRUD<AttendanceDetail,Integer>{

    // Punto 13: cuenta las inasistencias consecutivas de un Discipulo
    // en su celula (reporte sin efectos secundarios; la baja sigue
    // siendo una decision manual).
    Integer countConsecutiveAbsences(Integer idMemberCell);

}

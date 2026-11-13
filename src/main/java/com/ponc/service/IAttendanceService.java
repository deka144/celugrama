package com.ponc.service;

import com.ponc.model.Attendance;
import org.springframework.data.domain.Page;

public interface IAttendanceService extends ICRUD<Attendance,Integer> {

    Page<Attendance> findPage(String search, int page, int size);
    // PAGINADO: busca asistencias (search puede ir vacío) y devuelve la página pedida
}

package com.ponc.service;

import com.ponc.model.Period;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPeriodService extends ICRUD<Period,Integer>{

    // Metodo nuevo: genera los periodos (semanas) del mes actual.
    List<Period> generarPeriodosDelMesActual();

    Page<Period> findPage(String search, int page, int size);
    // PAGINADO: busca períodos (search puede ir vacío) y devuelve la página pedida
}

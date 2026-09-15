package com.ponc.service;

import com.ponc.model.Period;

import java.util.List;

public interface IPeriodService extends ICRUD<Period,Integer>{

    // Metodo nuevo: genera los periodos (semanas) del mes actual.
    List<Period> generarPeriodosDelMesActual();
}

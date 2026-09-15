package com.ponc.repo;

import com.ponc.model.Period;

public interface IPeriodRepo extends IGenericRepo<Period,Integer> {

    // Spring Data genera la consulta automaticamente a partir del nombre del metodo
    // (equivale al "if not exists(select * from period where months=.. and years=..)"
    // que tenia tu procedimiento periodos_mes en SQL).
    boolean existsByMonthsAndYears(Integer months, Integer years);
}

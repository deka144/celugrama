package com.ponc.repo;

import com.ponc.model.Period;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPeriodRepo extends IGenericRepo<Period,Integer> {

    // Spring Data genera la consulta automaticamente a partir del nombre del metodo
    // (equivale al "if not exists(select * from period where months=.. and years=..)"
    // que tenia tu procedimiento periodos_mes en SQL).
    boolean existsByMonthsAndYears(Integer months, Integer years);

    // PAGINADO: busca períodos por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT pe FROM Period pe " + // PAGINADO: trae los registros de períodos (alias pe)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR CAST(pe.week AS string) LIKE CONCAT('%', :search, '%') " + // PAGINADO: o la semana (número, comparado como texto) contiene el texto
            "OR LOWER(pe.monthtxt) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el mes contiene el texto (sin importar mayúsculas)
            "OR LOWER(pe.theme1) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el tema 1 contiene el texto (sin importar mayúsculas)
            "OR LOWER(pe.theme2) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el tema 2 contiene el texto (sin importar mayúsculas)
            "ORDER BY pe.idPeriod") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<Period> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

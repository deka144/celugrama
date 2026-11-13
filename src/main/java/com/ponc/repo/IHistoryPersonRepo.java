package com.ponc.repo;

import com.ponc.model.HistoryPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IHistoryPersonRepo extends IGenericRepo<HistoryPerson,Integer> {

    // PAGINADO: busca historiales de persona por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT h FROM HistoryPerson h " + // PAGINADO: trae los registros de historiales de persona (alias h)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(h.nameTable) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre de la tabla contiene el texto (sin importar mayúsculas)
            "ORDER BY h.idHistoryPerson") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<HistoryPerson> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

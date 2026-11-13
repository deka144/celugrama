package com.ponc.repo;

import com.ponc.model.Cell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICellRepo extends IGenericRepo<Cell,Integer> {

    // PAGINADO: busca células por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT c FROM Cell c " + // PAGINADO: trae los registros de células (alias c)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(c.address) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o la dirección contiene el texto (sin importar mayúsculas)
            "OR LOWER(c.reference) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o la referencia contiene el texto (sin importar mayúsculas)
            "OR LOWER(c.observations) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o las observaciones contiene el texto (sin importar mayúsculas)
            "ORDER BY c.idCell") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<Cell> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

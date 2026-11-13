package com.ponc.repo;

import com.ponc.model.SpiritualGrowth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ISpiritualGrowthRepo extends IGenericRepo<SpiritualGrowth,Integer> {
    // PAGINADO: busca crecimientos espirituales por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT sg FROM SpiritualGrowth sg " + // PAGINADO: trae los registros de crecimientos espirituales (alias sg)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(sg.description) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o la descripción contiene el texto (sin importar mayúsculas)
            "ORDER BY sg.idSpiritualGrowth") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<SpiritualGrowth> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

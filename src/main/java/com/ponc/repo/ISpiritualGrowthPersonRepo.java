package com.ponc.repo;

import com.ponc.model.SpiritualGrowthPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ISpiritualGrowthPersonRepo extends IGenericRepo<SpiritualGrowthPerson,Integer> {

    boolean existsByPerson_IdPerson(Integer idPerson); // NUEVO: ¿esta persona tiene crecimientos espirituales? (para no eliminar la persona)
    boolean existsBySpiritualGrowth_IdSpiritualGrowth(Integer idSpiritualGrowth); // NUEVO: ¿está asignado a alguna persona? (para no eliminar el crecimiento espiritual)

    // PAGINADO: busca crecimientos espirituales de personas por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT sgp FROM SpiritualGrowthPerson sgp " + // PAGINADO: trae los registros de crecimientos espirituales de personas (alias sgp)
            "LEFT JOIN sgp.person p " + // PAGINADO: une con la persona para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "LEFT JOIN sgp.spiritualGrowth sg " + // PAGINADO: une con el crecimiento espiritual para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.paternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido paterno de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.maternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido materno de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(sg.description) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o la descripción contiene el texto (sin importar mayúsculas)
            "ORDER BY sgp.idSpiritualGrowthPerson") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<SpiritualGrowthPerson> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

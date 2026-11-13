package com.ponc.repo;

import com.ponc.model.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGuestRepo extends IGenericRepo<Guest,Integer> {
    // Punto 6: un invitado solo puede registrar asistencia en UNA
    // celula por periodo (sin importar cual).
    boolean existsByPerson_IdPersonAndPeriod_IdPeriod(Integer idPerson, Integer idPeriod);

    // Punto 7: historial del invitado en una celula especifica,
    // ordenado del periodo mas viejo al mas nuevo, para revisar si
    // los ultimos 5 son consecutivos.
    List<Guest> findByPerson_IdPersonAndCell_IdCellOrderByPeriod_StartDateAsc(Integer idPerson, Integer idCell);

    // Eliminar: ¿hay invitados de esta persona, de esta célula o de este período?
    boolean existsByPerson_IdPerson(Integer idPerson); // NUEVO
    boolean existsByCell_IdCell(Integer idCell); // NUEVO
    boolean existsByPeriod_IdPeriod(Integer idPeriod); // NUEVO

    // PAGINADO: busca invitados por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT g FROM Guest g " + // PAGINADO: trae los registros de invitados (alias g)
            "LEFT JOIN g.person p " + // PAGINADO: une con la persona para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.paternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido paterno de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.maternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido materno de la persona contiene el texto (sin importar mayúsculas)
            "ORDER BY g.idGuest") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<Guest> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

package com.ponc.repo;

import com.ponc.model.ChangeCell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IChangeCellRepo extends IGenericRepo<ChangeCell,Integer> {

    // Eliminar un miembro: ¿aparece como miembro anterior o como miembro nuevo en algún cambio de célula?
    boolean existsByMemberCellOld_IdMemberCell(Integer idMemberCell); // NUEVO
    boolean existsByMemberCellNew_IdMemberCell(Integer idMemberCell); // NUEVO

    // PAGINADO: busca cambios de célula por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT cc FROM ChangeCell cc " + // PAGINADO: trae los registros de cambios de célula (alias cc)
            "LEFT JOIN cc.memberCellOld mo " + // PAGINADO: une con el miembro anterior para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "LEFT JOIN mo.person po " + // PAGINADO: une con la persona del miembro anterior para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "LEFT JOIN cc.memberCellNew mn " + // PAGINADO: une con el miembro nuevo para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "LEFT JOIN mn.person pn " + // PAGINADO: une con la persona del miembro nuevo para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(po.name) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre del miembro anterior contiene el texto (sin importar mayúsculas)
            "OR LOWER(po.paternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido paterno del miembro anterior contiene el texto (sin importar mayúsculas)
            "OR LOWER(po.maternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido materno del miembro anterior contiene el texto (sin importar mayúsculas)
            "OR LOWER(pn.name) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre del miembro nuevo contiene el texto (sin importar mayúsculas)
            "OR LOWER(pn.paternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido paterno del miembro nuevo contiene el texto (sin importar mayúsculas)
            "OR LOWER(pn.maternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido materno del miembro nuevo contiene el texto (sin importar mayúsculas)
            "ORDER BY cc.idChangeCell") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<ChangeCell> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño


}

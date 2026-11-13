package com.ponc.repo;

import com.ponc.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAttendanceRepo extends IGenericRepo<Attendance,Integer> {
    boolean existsByCell_IdCell(Integer idCell); // NUEVO: ¿esta célula tiene asistencias? (para no eliminar la célula)
    boolean existsByPeriod_IdPeriod(Integer idPeriod); // NUEVO: ¿este período tiene asistencias? (para no eliminar el período)

    // PAGINADO: busca asistencias por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT a FROM Attendance a " + // PAGINADO: trae los registros de asistencias (alias a)
            "LEFT JOIN a.cell c " + // PAGINADO: une con la célula para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(c.address) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o la dirección contiene el texto (sin importar mayúsculas)
            "ORDER BY a.idAttendance") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<Attendance> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

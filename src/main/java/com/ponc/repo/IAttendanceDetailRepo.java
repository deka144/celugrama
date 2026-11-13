package com.ponc.repo;

import com.ponc.model.AttendanceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IAttendanceDetailRepo extends IGenericRepo<AttendanceDetail,Integer> {


    // Punto 5: la asistencia se registra una sola vez por periodo, por discipulo.
    boolean existsByAttendance_IdAttendanceAndMemberCell_IdMemberCell(Integer idAttendance, Integer idMemberCell);

    // Punto 13: historial de asistencia de un Discipulo en su celula,
    // del periodo mas reciente al mas antiguo -- para contar las
    // inasistencias consecutivas desde el periodo actual hacia atras.
    List<AttendanceDetail> findByMemberCell_IdMemberCellOrderByAttendance_Period_StartDateDesc(Integer idMemberCell);

    // Requerimiento 16: cuenta inasistencias de un MemberCell dentro
    // de un rango de fechas, sin cortarse en la primera asistencia
    // (a diferencia del reporte de racha del Punto 13). El rango se
    // compara contra el startDate del Period de cada Attendance.
    @Query("SELECT COUNT(ad) FROM AttendanceDetail ad " + // cuenta los registros de asistencia (AttendanceDetail)
            "WHERE ad.memberCell.idMemberCell = :idMemberCell " + // solo de este MemberCell
            "AND ad.attended = false " + // solo las inasistencias (attended = false)
            "AND ad.attendance.period.startDate BETWEEN :start AND :end") // dentro del rango, segun la fecha de inicio del Period
    Long countAbsencesByMemberCellAndDateRange( // devuelve el total (Long, como los otros count)
                                                @Param("idMemberCell") Integer idMemberCell, // el MemberCell a consultar
                                                @Param("start") LocalDateTime start, // inicio del rango
                                                @Param("end") LocalDateTime end); // fin del rango

    // Eliminar: ¿esta asistencia tiene detalles? ¿este miembro tiene detalles de asistencia?
    boolean existsByAttendance_IdAttendance(Integer idAttendance); // NUEVO
    boolean existsByMemberCell_IdMemberCell(Integer idMemberCell); // NUEVO

    // PAGINADO: busca detalles de asistencia por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT ad FROM AttendanceDetail ad " + // PAGINADO: trae los registros de detalles de asistencia (alias ad)
            "LEFT JOIN ad.memberCell mc " + // PAGINADO: une con el miembro de célula para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "LEFT JOIN mc.person p " + // PAGINADO: une con la persona para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.paternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido paterno de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.maternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido materno de la persona contiene el texto (sin importar mayúsculas)
            "ORDER BY ad.idAttendanceDetail") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<AttendanceDetail> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

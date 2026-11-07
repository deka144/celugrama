package com.ponc.repo;

import com.ponc.model.Attendance;

public interface IAttendanceRepo extends IGenericRepo<Attendance,Integer> {
    boolean existsByCell_IdCell(Integer idCell); // NUEVO: ¿esta célula tiene asistencias? (para no eliminar la célula)
    boolean existsByPeriod_IdPeriod(Integer idPeriod); // NUEVO: ¿este período tiene asistencias? (para no eliminar el período)

}

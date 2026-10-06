package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Attendance;
import com.ponc.model.AttendanceDetail;
import com.ponc.model.MemberCell;
import com.ponc.repo.IAttendanceDetailRepo;
import com.ponc.repo.IAttendanceRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IMemberCellRepo;
import com.ponc.service.IAttendanceDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceDetailServiceImpl extends CRUDImpl<AttendanceDetail, Integer> implements IAttendanceDetailService {
    private final IAttendanceDetailRepo repo;
    private final IAttendanceRepo attendanceRepo; // punto 4: para traer la Attendance real
    private final IMemberCellRepo memberCellRepo; // punto 4: para traer el MemberCell real

    @Override
    protected IGenericRepo<AttendanceDetail, Integer> getRepo() {
        return repo;
    }

//    @Override
//    public AttendanceDetail save(AttendanceDetail t){
//        try{
//            return super.save(t);
//
//        } catch (Exception ex){
//            throw new ApiException("Error al crear Dettalle de Asistencia");
//        }
//    }

    @Override
    @Transactional // puntos 4 y 5: no escribe en HistoryPerson, pero conviene igual para evitar condiciones de carrera
    public AttendanceDetail save(AttendanceDetail t){
        try{
            // Lo que llega en "t" son shells armados por ModelMapper a
            // partir del AttendanceDetailDTO (solo con el ID seteado);
            // hace falta traer las entidades reales para comparar sus datos.
            Attendance attendance = attendanceRepo.findById(t.getAttendance().getIdAttendance())
                    .orElseThrow(() -> new ApiException("La asistencia indicada no existe."));
            MemberCell memberCell = memberCellRepo.findById(t.getMemberCell().getIdMemberCell())
                    .orElseThrow(() -> new ApiException("El miembro de célula indicado no existe."));

            // Punto 4: un Discipulo no puede registrar asistencia de un
            // periodo en una celula distinta a la suya.
            if (!memberCell.getCell().getIdCell().equals(attendance.getCell().getIdCell())) {
                throw new ApiException("Este miembro no pertenece a la célula de esta asistencia.");
            }

            // Punto 5: la asistencia se registra una sola vez por
            // periodo, por discipulo.
            if (repo.existsByAttendance_IdAttendanceAndMemberCell_IdMemberCell(attendance.getIdAttendance(), memberCell.getIdMemberCell())) {
                throw new ApiException("Ya existe un registro de asistencia para este miembro en este periodo.");
            }

            t.setAttendance(attendance);
            t.setMemberCell(memberCell);

            return super.save(t);

        } catch (ApiException ex) {
            throw ex; // deja pasar el mensaje de validacion tal cual
        } catch (Exception ex){
            throw new ApiException("Error al crear Dettalle de Asistencia");
        }
    }


    @Override
    public AttendanceDetail update(AttendanceDetail t, Integer id){
        try{
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex;//deja el 404 tal como ya funciona
        }catch (Exception ex){
            throw new ApiException("Error al actualizar Dettalle de Asistencia");
        }
    }

    @Override
    public List<AttendanceDetail> findAll(){
        try{
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los detalles de asistencia");
        }
    }

    @Override
    public AttendanceDetail findById(Integer id){
        try {
            return super.findById(id);
        }catch (ModelNotFoundException ex){
            throw ex;// deja el 404 tal como ya funciona
        } catch (Exception e) {
            throw new ApiException("Error al buscar detalle de asistencia");
        }
    }

    @Override
    public void deleteById(Integer id){
        try{
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex;// deja el 404 tal como ya funciona
        }
        catch (Exception e) {
            throw new ApiException("Error al eliminar detalle de asistencia");
        }
    }

    @Override
    public Integer countConsecutiveAbsences(Integer idMemberCell) {
        try {
            // Reporte del punto 13, sin efectos secundarios: no da de baja
            // a nadie, solo informa para que un humano decida.
            List<AttendanceDetail> history = repo.findByMemberCell_IdMemberCellOrderByAttendance_Period_StartDateDesc(idMemberCell);
            int consecutiveCount = 0;
            for (AttendanceDetail detail : history) {
                if (Boolean.FALSE.equals(detail.getAttended())) {
                    consecutiveCount++;
                } else {
                    break; // se corta apenas aparece una asistencia
                }
            }
            return consecutiveCount;
        } catch (Exception ex) {
            throw new ApiException("Error al contar las inasistencias consecutivas.");
        }
    }

    @Override // implementa el metodo de IAttendanceDetailService
    public Long countAbsencesByDateRange(Integer idMemberCell, LocalDateTime start, LocalDateTime end) { // Requerimiento 16: cuenta las inasistencias del rango
        try { // un error inesperado sale con un mensaje propio
            // Requerimiento 16: reporte sin efectos secundarios, igual
            // que countConsecutiveAbsences() -- no da de baja a nadie.
            return repo.countAbsencesByMemberCellAndDateRange(idMemberCell, start, end); // delega la cuenta en el repo
        } catch (Exception ex) { // si algo falla...
            throw new ApiException("Error al contar las inasistencias del rango indicado."); // ...responde con un mensaje generico
        } // cierra el try/catch
    } // cierra el metodo



}

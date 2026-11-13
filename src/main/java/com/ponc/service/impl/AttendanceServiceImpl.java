package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Attendance;
import com.ponc.model.AttendanceDetail;
import com.ponc.model.Cell;
import com.ponc.repo.IAttendanceDetailRepo;
import com.ponc.repo.IAttendanceRepo;
import com.ponc.repo.ICellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IAttendanceService;
import com.ponc.util.CellScheduleUtil;
import com.ponc.util.DeleteCheckUtil;
import com.ponc.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl extends CRUDImpl<Attendance,Integer> implements IAttendanceService {

    private final IAttendanceRepo repo;
    private final IAttendanceDetailRepo attendanceDetailRepo; // NUEVO: para revisar si otro registro usa este
    private final ICellRepo  cellRepo; // punto 10: dia/hora reales de la celula


    @Override
    protected IGenericRepo<Attendance, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional // punto 10: no escribe en historyPerson, pero conviene igual para evitar condiciones de carrera
    public Attendance save(Attendance t){
        try{
            Cell cell = cellRepo.findById(t.getCell().getIdCell())
                    .orElseThrow(() -> new ApiException("La célula indicada no existe."));

            // Punto 10: solo se puede registrar el dia y dentro de la
            // ventana de horario de la celula.
            CellScheduleUtil.validateRegistrationWithinSchedule(cell);

            return super.save(t);

        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex){
            throw new ApiException("Error al crear Asistencia");
        }

    }

    @Override
    public Attendance update(Attendance t, Integer id){
        try{
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex;//deja el 404 tal como ya funciona
        }catch (Exception ex){
            throw new ApiException("Error al actualizar  Asistencia");
        }
    }

    @Override
    public List<Attendance> findAll(){
        try{
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener las asistencias");
        }
    }

    @Override
    public Attendance findById(Integer id){
        try {
            return super.findById(id);
        }catch (ModelNotFoundException ex){
            throw ex;// deja el 404 tal como ya funciona
        } catch (Exception e) {
            throw new ApiException("Error al buscar asistencia");
        }
    }

    @Override
    public void deleteById(Integer id){
        try{
            super.findById(id); // NUEVO: si el id no existe lanza el 404, antes de revisar las relaciones
            validateNoRelations(id);// NUEVO: si otro registro lo usa, lanza el 409 con el motivo
            super.deleteById(id);
        } catch (ApiException ex) {
            throw ex; // NUEVO: deja pasar el mensaje 409 tal cual
        }catch (ModelNotFoundException ex) {
            throw ex;// deja el 404 tal como ya funciona
        }
        catch (Exception e) {
            throw new ApiException("Error al eliminar asistencia");
        }
    }

    // NUEVO: lanza 409 si otro registro usa esta asistencia. Cada motivo que encuentra se suma a la lista.
    private void validateNoRelations(Integer id) {
        List<String> reasons = new ArrayList<>();
        if (attendanceDetailRepo.existsByAttendance_IdAttendance(id)) {
            reasons.add("tiene detalles de asistencia registrados");
        }
        DeleteCheckUtil.failIfRelated("la asistencia", reasons);
    }

    @Override // PAGINADO: implementa findPage de la interfaz
    public Page<Attendance> findPage(String search, int page, int size) { // PAGINADO: busca asistencias y devuelve una página
        return repo.search(PageUtil.text(search), PageUtil.pageable(page, size)); // PAGINADO: limpia el texto, arma la paginación y consulta al repositorio
    }


}

package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Attendance;
import com.ponc.model.AttendanceDetail;
import com.ponc.repo.IAttendanceRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IAttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl extends CRUDImpl<Attendance,Integer> implements IAttendanceService {

    private final IAttendanceRepo repo;

    @Override
    protected IGenericRepo<Attendance, Integer> getRepo() {
        return repo;
    }

    @Override
    public Attendance save(Attendance t){
        try{
            return super.save(t);

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
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex;// deja el 404 tal como ya funciona
        }
        catch (Exception e) {
            throw new ApiException("Error al eliminar asistencia");
        }
    }
}

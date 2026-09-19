package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Attendance;
import com.ponc.model.AttendanceDetail;
import com.ponc.repo.IAttendanceDetailRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IAttendanceDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceDetailServiceImpl extends CRUDImpl<AttendanceDetail, Integer> implements IAttendanceDetailService {
    private final IAttendanceDetailRepo repo;

    @Override
    protected IGenericRepo<AttendanceDetail, Integer> getRepo() {
        return repo;
    }

    @Override
    public AttendanceDetail save(AttendanceDetail t){
        try{
            return super.save(t);

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

}

package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.HistoryPerson;
import com.ponc.model.HistoryPerson;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IHistoryPersonRepo;
import com.ponc.service.IHistoryPersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryPersonServiceImpl extends CRUDImpl<HistoryPerson,Integer> implements IHistoryPersonService {

    private final IHistoryPersonRepo repo;

    @Override
    protected IGenericRepo<HistoryPerson, Integer> getRepo() {
        return repo;
    }

    @Override
    public HistoryPerson save(HistoryPerson t) {
        try {
            return super.save(t);
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Historial de la persona.");
        }
    }

    @Override
    public HistoryPerson update(HistoryPerson t, Integer id) {
        try {
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el Historial de la persona.");
        }
    }

    @Override
    public List<HistoryPerson> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los Historiales de las personas.");
        }
    }

    @Override
    public HistoryPerson findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Historial de la persona.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar el Historial de la persona.");
        }
    }
}

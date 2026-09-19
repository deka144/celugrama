package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.ChangeCell;
import com.ponc.model.ChangeCell;
import com.ponc.repo.IChangeCellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IChangeCellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChangeCellServiceImpl extends CRUDImpl<ChangeCell, Integer> implements IChangeCellService {

    private final IChangeCellRepo repo;

    @Override
    protected IGenericRepo<ChangeCell, Integer> getRepo() {
        return repo;
    }

    @Override
    public ChangeCell save(ChangeCell t) {
        try {
            return super.save(t);
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Cambio de Célula.");
        }
    }

    @Override
    public ChangeCell update(ChangeCell t, Integer id) {
        try {
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el Cambio de Célula.");
        }
    }

    @Override
    public List<ChangeCell> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los Cambios de Célula.");
        }
    }

    @Override
    public ChangeCell findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Cambio de Célula");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar el Cambio de Célula.");
        }
    }
}

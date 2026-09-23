package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.MemberCell;
import com.ponc.model.MemberCell;
import com.ponc.repo.IMemberCellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IMemberCellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberCellServiceImpl extends CRUDImpl<MemberCell,Integer> implements IMemberCellService {

    private final IMemberCellRepo repo;

    @Override
    protected IGenericRepo<MemberCell, Integer> getRepo() {
        return repo;
    }

    @Override
    public MemberCell save(MemberCell t) {
        try {
            return super.save(t);
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Miembro de Célula.");
        }
    }

    @Override
    public MemberCell update(MemberCell t, Integer id) {
        try {
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el Miembro de Célula.");
        }
    }

    @Override
    public List<MemberCell> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los Miembros de Célula.");
        }
    }

    @Override
    public MemberCell findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Miembro de Célula.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar el Miembro de Célula.");
        }
    }
}

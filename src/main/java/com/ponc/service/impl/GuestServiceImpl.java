package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Guest;
import com.ponc.model.Guest;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IGuestRepo;
import com.ponc.service.IGuestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestServiceImpl extends CRUDImpl<Guest, Integer> implements IGuestService {

    private final IGuestRepo repo;
    @Override
    protected IGenericRepo<Guest, Integer> getRepo() {
        return repo;
    }

    @Override
    public Guest save(Guest t) {
        try {
            return super.save(t);
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Invitado");
        }
    }

    @Override
    public Guest update(Guest t, Integer id) {
        try {
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el invitado.");
        }
    }

    @Override
    public List<Guest> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los invitados.");
        }
    }

    @Override
    public Guest findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Invitado.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar el Invitado.");
        }
    }
}

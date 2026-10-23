package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.SpiritualGrowth;
import com.ponc.model.SpiritualGrowth;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.ISpiritualGrowthRepo;
import com.ponc.service.ISpiritualGrowthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SpiritualGrowthServiceImpl extends CRUDImpl<SpiritualGrowth, Integer> implements ISpiritualGrowthService {
    private final ISpiritualGrowthRepo repo;

    @Override
    protected IGenericRepo<SpiritualGrowth, Integer> getRepo() {
        return repo;
    }

    @Override
    public SpiritualGrowth save(SpiritualGrowth t) {
        try {
            return super.save(t);
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Crecimiento Espiritual.");
        }
    }

    @Override
    public SpiritualGrowth update(SpiritualGrowth t, Integer id) {
        try {
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el Crecimiento Espiritual.");
        }
    }

    @Override
    public List<SpiritualGrowth> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los Crecimientos Espirituales.");
        }
    }

    @Override
    public SpiritualGrowth findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Crecimiento Espiritual.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar el Crecimiento Espiritual.");
        }
    }

    @Override // implementa el método nuevo de ISpiritualGrowthService
    public SpiritualGrowth changeState(Integer id, Boolean state) { // cambia solo el estado del crecimiento espiritual
        try { // cualquier error inesperado sale con un mensaje propio
            SpiritualGrowth obj = super.findById(id); // busca el crecimiento espiritual; si no existe lanza ModelNotFoundException (404)
            obj.setState(state); // cambia únicamente el atributo state
            return super.save(obj); // guarda directo en la base, sin pasar por las validaciones del save() de esta clase
        } catch (ModelNotFoundException ex) { // si el id no existe...
            throw ex; // ...deja el 404 tal como ya funciona
        } catch (Exception ex) { // si falla cualquier otra cosa...
            throw new ApiException("Error al cambiar el estado del Crecimiento Espiritual."); // ...responde con un mensaje propio
        } // cierra el try/catch
    } // cierra el método

}

package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.HistoryPerson;
import com.ponc.model.SpiritualGrowthPerson;
import com.ponc.model.SpiritualGrowthPerson;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.ISpiritualGrowthPersonRepo;
import com.ponc.service.IHistoryPersonService;
import com.ponc.service.ISpiritualGrowthPersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SpiritualGrowthPersonServiceImpl extends CRUDImpl<SpiritualGrowthPerson, Integer> implements ISpiritualGrowthPersonService {
    private final ISpiritualGrowthPersonRepo repo;
    private final IHistoryPersonService historyPersonService; // Punto 9

    @Override
    protected IGenericRepo<SpiritualGrowthPerson, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional // Punto 9: el insert y el registro en HistoryPErson son atomicos
    public SpiritualGrowthPerson save(SpiritualGrowthPerson t) {
        try {
            SpiritualGrowthPerson saved = super.save(t);

            // Punto 9: registrar en el historial de la persona.
            HistoryPerson history = new HistoryPerson();
            history.setNameTable("spiritual_growth_person");
            history.setIdTable(saved.getIdSpiritualGrowthPerson());
            history.setRegisterDate(LocalDateTime.now());
            historyPersonService.save(history);

            return saved;
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Crecimiento Espiritual de la Persona.");
        }
    }

    @Override
    public SpiritualGrowthPerson update(SpiritualGrowthPerson t, Integer id) {
        try {
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el Crecimiento Espiritual de la Persona.");
        }
    }

    @Override
    public List<SpiritualGrowthPerson> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los Crecimientos Espirituales de las Personas.");
        }
    }

    @Override
    public SpiritualGrowthPerson findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Crecimiento Espiritual de la Persona.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar el Crecimiento Espiritual de la Persona.");
        }
    }
}

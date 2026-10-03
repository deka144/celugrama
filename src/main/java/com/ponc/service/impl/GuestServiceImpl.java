package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.*;
import com.ponc.model.enums.MemberType;
import com.ponc.repo.ICellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IGuestRepo;
import com.ponc.repo.IMemberCellRepo;
import com.ponc.service.IGuestService;
import com.ponc.service.IHistoryPersonService;
import com.ponc.service.IMemberCellService;
import com.ponc.util.CellScheduleUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GuestServiceImpl extends CRUDImpl<Guest, Integer> implements IGuestService {

    private static final int PROMOTION_STREAK_LENGTH = 5; // punto 7

    private final IGuestRepo repo;
    private final ICellRepo cellRepo;// punto 10: dia/hora reales de la celula
    private final IMemberCellRepo memberCellRepo; // punto 6: ya es miembro activo?
    private final IMemberCellService memberCellService; // punto 7: crea el MemberCell al promover
    private final IHistoryPersonService historyPersonService; // Punto 9

    @Override
    protected IGenericRepo<Guest, Integer> getRepo() {
        return repo;
    }

    @Override
    @Transactional // puntos 6, 7 y 9: el insert del invitado, la posible promocion a Discipulo y el registro en HistoryPerson son atomicos
    public Guest save(Guest t) {
        try {

            Cell cell = cellRepo.findById(t.getCell().getIdCell())
                    .orElseThrow(() -> new ApiException("La célula indicada no existe."));

            // Punto 10: solo se puede registrar en el dia y dentro de la
            // ventana de horario de la celula.
            CellScheduleUtil.validateRegistrationWithinSchedule(cell);


            // Punto 6 (parte 1): una celula por periodo, sin importar cual.
            if (repo.existsByPerson_IdPersonAndPeriod_IdPeriod(t.getPerson().getIdPerson(), t.getPeriod().getIdPeriod())) {
                throw new ApiException("Esta persona ya registró asistencia como invitado en este periodo.");
            }

            // Punto 6 (parte 2): no puede ser invitado si ya es miembro
            // activo (Lider o Discipulo) de alguna celula.
            if (memberCellRepo.existsByPerson_IdPersonAndStateTrue(t.getPerson().getIdPerson())) {
                throw new ApiException("Esta persona ya es miembro de una célula, no puede registrarse como invitado.");
            }

            //return super.save(t);

            Guest saved = super.save(t);

            //punto 9: registrar el historial de la persona.
            HistoryPerson history= new HistoryPerson();
            history.setNameTable("guest");
            history.setIdTable(saved.getIdGuest());
            history.setRegisterDate(LocalDateTime.now());
            historyPersonService.save(history);

            // Punto 7: si con este registro completa 5 periodos
            // consecutivos en la misma celula, se promueve a Discipulo.
            verifyAndPromoteGuest(saved);


            return saved;

        } catch (ApiException ex) {
            throw ex; // deja pasar el mensaje de validacion tal cual
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Invitado");
        }

    }

    // Punto 7: revisa el historial del invitado en esa celula
    // especifica. Si los ultimos 5 periodos son consecutivos (7 dias
    // exactos entre el inicio de uno y el del siguiente), lo promueve
    // automaticamente a Discipulo de esa celula.
    private void verifyAndPromoteGuest(Guest guest) throws Exception {
        List<Guest> history= repo.findByPerson_IdPersonAndCell_IdCellOrderByPeriod_StartDateAsc(
                guest.getPerson().getIdPerson(), guest.getCell().getIdCell());

        if (history.size() < PROMOTION_STREAK_LENGTH) {
            return; // todavia no llega a 5
        }

        List<Guest> lastFive = history.subList(history.size() - PROMOTION_STREAK_LENGTH, history.size());
        for (int i = 1; i < lastFive.size(); i++) {
            LocalDateTime previus = lastFive.get(i - 1).getPeriod().getStartDate();
            LocalDateTime current = lastFive.get(i).getPeriod().getStartDate();
            if (!current.isEqual(previus.plusDays(7))) {
                return; // se corto la racha (cambio de celula u omitio un periodo)
            }
        }

        MemberCell newMemberCell = new MemberCell();
        newMemberCell.setCell(guest.getCell());
        newMemberCell.setPerson(guest.getPerson());
        newMemberCell.setTypeMemberCell(MemberType.DISCIPULO);
        newMemberCell.setState(true);
        memberCellService.save(newMemberCell); // ya registra su propio hisotryPErson (punto 9)
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

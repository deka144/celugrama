package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.*;
import com.ponc.repo.IChangeCellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IMemberCellRepo;
import com.ponc.service.IChangeCellService;
import com.ponc.service.IHistoryPersonService;
import com.ponc.service.IMemberCellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ChangeCellServiceImpl extends CRUDImpl<ChangeCell, Integer> implements IChangeCellService {

    private final IChangeCellRepo repo;
    private final IMemberCellRepo memberCellRepo;// Punto 8: leer y desactivar el memberCell viejo
    private final IMemberCellService memberCellService; // Punto 8: crear el MemberCell Nuevo
    private final IHistoryPersonService historyPersonService; // Punto 9

    @Override
    protected IGenericRepo<ChangeCell, Integer> getRepo() {
        return repo;
    }


    // Punto 8: cambia a un Discipulo de celula, toda en unsa sola
    // operacion : 1) desactiva (state=false) el MemberCell viejo,
    // 2) crea un MemberCell nuevo en la celula nueva, 3) crea el
    // ChangeCell que enlaza ambos IDs, 4) registra el ChangeCell en
    // el historial (punto 9). las 4 escrituras son atomicas.
    @Override
    @Transactional // puntos 8 y 9 : las 4 escrituras de arriba son atomicas
    public ChangeCell changeCell(Integer idMemberCellOld, Integer idCellNew) {
        try{
            MemberCell oldMemberCell = memberCellRepo.findById(idMemberCellOld)
                    .orElseThrow(()-> new ApiException("El Miembro de Célula indicado no Existe"));

            oldMemberCell.setState(false);
            memberCellRepo.save(oldMemberCell);

            Cell newCell = new Cell();
            newCell.setIdCell(idCellNew);

            MemberCell newMemberCell = new MemberCell();
            newMemberCell.setCell(newCell);
            newMemberCell.setPerson(oldMemberCell.getPerson());
            newMemberCell.setTypeMemberCell(oldMemberCell.getTypeMemberCell());
            newMemberCell.setState(true);
            MemberCell saveNewMemberCell = memberCellService.save(newMemberCell); //ya registra su historial (punto 9)

            ChangeCell change = new ChangeCell();
            change.setMemberCellOld(oldMemberCell);
            change.setMemberCellNew(saveNewMemberCell);
            change.setChangeDate(LocalDateTime.now());
            ChangeCell saved = super.save(change);

            //punto 9: registrar en el historial de la persona.
            HistoryPerson history= new HistoryPerson();
            history.setNameTable("change_cell");
            history.setIdTable(saved.getIdChangeCell());
            history.setRegisterDate(LocalDateTime.now());
            historyPersonService.save(history);

            return saved;

        } catch (ApiException ex){
            throw  ex;
        }catch (Exception ex){
            throw new ApiException("Error al cambiar de célula al miembro");
        }
    }

    // Punto 12: cambia el Lider de una celula, todo en una sola
    // operacion: 1) desactiva (state=false) el MemberCell del Lider
    // viejo, 2) crea un MemberCell nuevo en la MISMA celula con la
    // persona nueva (al reves del changeCell: aca la celula se
    // mantiene y la persona cambia), 3) crea el ChangeCell que enlaza
    // ambos IDs, 4) registra el ChangeCell en el historial (punto 9).
    // Las 4 escrituras son atomicas.
    @Override
    @Transactional // puntos 9 y 12: las 4 escrituras de arriba son atomicas
    public ChangeCell changeCellLeader(Integer idMemberCellOldLeader, Integer idPersonNewLeader) {
        try {
            MemberCell oldLeader = memberCellRepo.findById(idMemberCellOldLeader)
                    .orElseThrow(() -> new ApiException("El Miembro de Célula indicado no existe."));

            oldLeader.setState(false);
            memberCellRepo.save(oldLeader);

            Person newPerson = new Person();
            newPerson.setIdPerson(idPersonNewLeader);

            MemberCell newLeader = new MemberCell();
            newLeader.setCell(oldLeader.getCell()); // misma celula que el Lider viejo
            newLeader.setPerson(newPerson);
            newLeader.setTypeMemberCell(oldLeader.getTypeMemberCell()); // LIDER
            newLeader.setState(true);
            // memberCellService.save() ya valida solo los puntos 1 y 11
            // (celula sin otro Lider activo, Lider sin 2 celulas de
            // Discipulado) y registra su propio HistoryPerson (punto 9).
            MemberCell savedNewLeader = memberCellService.save(newLeader);

            ChangeCell change = new ChangeCell();
            change.setMemberCellOld(oldLeader);
            change.setMemberCellNew(savedNewLeader);
            change.setChangeDate(LocalDateTime.now());
            ChangeCell saved = super.save(change);

            HistoryPerson history = new HistoryPerson();
            history.setNameTable("change_cell");
            history.setIdTable(saved.getIdChangeCell());
            history.setRegisterDate(LocalDateTime.now());
            historyPersonService.save(history);

            return saved;
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException("Error al cambiar el Líder de la célula.");
        }
    }


    @Override
    @Transactional // punto 9: el insert y el registro en historyPerson son atomicos
    public ChangeCell save(ChangeCell t) {
        try {
            ChangeCell saved = super.save(t);

            // Punto 9: registrar en el histoiral de la persona
            HistoryPerson history= new HistoryPerson();
            history.setNameTable("change_cell");
            history.setIdTable(saved.getIdChangeCell());
            history.setRegisterDate(LocalDateTime.now());
            historyPersonService.save(history);

            return saved;
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

package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Cell;
import com.ponc.model.HistoryPerson;
import com.ponc.model.MemberCell;
import com.ponc.model.MemberCell;
import com.ponc.model.enums.CellType;
import com.ponc.model.enums.MemberType;
import com.ponc.repo.ICellRepo;
import com.ponc.repo.IMemberCellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IHistoryPersonService;
import com.ponc.service.IMemberCellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberCellServiceImpl extends CRUDImpl<MemberCell,Integer> implements IMemberCellService {

    private final IMemberCellRepo repo;
    private final ICellRepo cellRepo;
    private final IHistoryPersonService historyPersonService;

    @Override
    protected IGenericRepo<MemberCell, Integer> getRepo() {
        return repo;
    }

//    @Override
//    public MemberCell save(MemberCell t) {
//        try {
//            return super.save(t);
//        } catch (Exception ex) {
//            throw new ApiException("Error al crear el Miembro de Célula.");
//        }
//    }
    @Override
    @Transactional
    public MemberCell save(MemberCell t){
        try {
            // Punto 1: una celula solo puede tener un Lider activo.
            if (t.getTypeMemberCell() == MemberType.LIDER
                    && repo.existsByCell_IdCellAndTypeMemberCellAndStateTrue(t.getCell().getIdCell(), MemberType.LIDER)) {
                throw new ApiException("Esta célula ya tiene un Líder activo.");
            }

            // Punto 11: un Lider puede tener varias celulas, pero solo una de
            // tipo Discipulado (las demas pueden ser Evangelismo sin limite).
            if (t.getTypeMemberCell() == MemberType.LIDER) {
                // El t.getCell() que llega solo trae el idCell (ModelMapper),
                // hay que traer la Cell real para leer su type.
                Cell newCell = cellRepo.findById(t.getCell().getIdCell())
                        .orElseThrow(() -> new ApiException("La célula indicada no existe."));
                if (newCell.getType() == CellType.DISCIPULADO
                        && repo.existsByPerson_IdPersonAndTypeMemberCellAndStateTrueAndCell_Type(
                        t.getPerson().getIdPerson(), MemberType.LIDER, CellType.DISCIPULADO)) {
                    throw new ApiException("Este Líder ya lidera otra célula de tipo Discipulado.");
                }
            }

            // Puntos 2 y 3: una persona solo puede ser Discipulo de UNA celula.
            if (t.getTypeMemberCell() == MemberType.DISCIPULO
                    && repo.existsByPerson_IdPersonAndTypeMemberCellAndStateTrue(t.getPerson().getIdPerson(), MemberType.DISCIPULO)) {
                throw new ApiException("Esta persona ya es Discípulo de otra célula.");
            }

            // Punto 13: en una celula de tipo Discipulado, cada Discipulo
            // tiene que ser tambien Lider activo de OTRA celula, y hay
            // tope de 12 Discipulos activos por celula.
            Cell discipleshipCell = null;
            if (t.getTypeMemberCell() == MemberType.DISCIPULO) {
                Cell discipleCell = cellRepo.findById(t.getCell().getIdCell())
                        .orElseThrow(() -> new ApiException("La célula indicada no existe."));
                if (discipleCell.getType() == CellType.DISCIPULADO) {
                    discipleshipCell = discipleCell;
                    if (!repo.existsByPerson_IdPersonAndTypeMemberCellAndStateTrueAndCell_IdCellNot(
                            t.getPerson().getIdPerson(), MemberType.LIDER, discipleCell.getIdCell())) {
                        throw new ApiException("Este Discípulo tiene que ser Líder activo de otra célula.");
                    }
                    Long activeDisciples = repo.countByCell_IdCellAndTypeMemberCellAndStateTrue(
                            discipleCell.getIdCell(), MemberType.DISCIPULO);
                    if (activeDisciples >= 12) {
                        throw new ApiException("Esta célula de Discipulado ya tiene el máximo de 12 Discípulos.");
                    }
                }
            }

            MemberCell saved = super.save(t);

            // Punto 13: si con este alta la celula de Discipulado llego a
            // 6 Discipulos activos, se activa sola (Cell.state=true). Solo
            // sube el estado, nunca lo baja desde aca (no hay baja
            // automatica, eso quedo definido como ajuste manual).
            if (discipleshipCell != null && !Boolean.TRUE.equals(discipleshipCell.getState())) {
                Long activeDisciplesNow = repo.countByCell_IdCellAndTypeMemberCellAndStateTrue(
                        discipleshipCell.getIdCell(), MemberType.DISCIPULO);
                if (activeDisciplesNow >= 6) {
                    discipleshipCell.setState(true);
                    cellRepo.save(discipleshipCell);
                }
            }

            // Punto 9: registrar la creacion en el historial de la persona.
            HistoryPerson history = new HistoryPerson();
            history.setNameTable("member_cell");
            history.setIdTable(saved.getIdMemberCell());
            history.setRegisterDate(LocalDateTime.now());
            historyPersonService.save(history);

            return saved;
        } catch (ApiException ex) {
            throw ex; // deja pasar el mensaje de validacion tal cual
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

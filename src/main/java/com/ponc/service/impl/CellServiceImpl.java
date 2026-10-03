package com.ponc.service.impl;

import com.ponc.dto.DiscipleshipCompositionResponseDTO;
import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Cell;
import com.ponc.model.Cell;
import com.ponc.model.enums.CellType;
import com.ponc.model.enums.MemberType;
import com.ponc.repo.ICellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IMemberCellRepo;
import com.ponc.service.ICellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CellServiceImpl extends CRUDImpl<Cell,Integer> implements ICellService {
    private final ICellRepo repo;
    private final IMemberCellRepo memberCellRepo;


    @Override
    protected IGenericRepo<Cell, Integer> getRepo() {
        return repo;
    }

    @Override
    public Cell save(Cell t){
        try{
            return super.save(t);

        } catch (Exception ex){
            throw new ApiException("Error al crear Célula");
        }
    }

    @Override
    public Cell update(Cell t, Integer id){
        try{
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex;//deja el 404 tal como ya funciona
        }catch (Exception ex){
            throw new ApiException("Error al actualizar Célula");
        }
    }

    @Override
    public List<Cell> findAll(){
        try{
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener Célula");
        }
    }

    @Override
    public Cell findById(Integer id){
        try {
            return super.findById(id);
        }catch (ModelNotFoundException ex){
            throw ex;// deja el 404 tal como ya funciona
        } catch (Exception e) {
            throw new ApiException("Error al buscar Célula");
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
            throw new ApiException("Error al eliminar Célula");
        }
    }

    @Override
    public DiscipleshipCompositionResponseDTO verifyDiscipleshipComposition(Integer idCell) {
        try {
            Cell cell = repo.findById(idCell)
                    .orElseThrow(() -> new ApiException("La célula indicada no existe."));
            if (cell.getType() != CellType.DISCIPULADO) {
                throw new ApiException("Esta célula no es de tipo Discipulado.");
            }

            Long activeDisciples = memberCellRepo.countByCell_IdCellAndTypeMemberCellAndStateTrue(
                    idCell, MemberType.DISCIPULO);
            Long disciplesStillLeadingElsewhere = memberCellRepo.countDisciplesStillLeadingElsewhere(
                    idCell, MemberType.DISCIPULO, MemberType.LIDER);

            return new DiscipleshipCompositionResponseDTO(
                    idCell,
                    activeDisciples,
                    disciplesStillLeadingElsewhere,
                    disciplesStillLeadingElsewhere >= 6);
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApiException("Error al verificar la composición de la célula.");
        }
    }

}

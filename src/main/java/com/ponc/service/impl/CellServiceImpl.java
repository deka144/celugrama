package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Cell;
import com.ponc.model.Cell;
import com.ponc.repo.ICellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.ICellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CellServiceImpl extends CRUDImpl<Cell,Integer> implements ICellService {
    private final ICellRepo repo;
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
}

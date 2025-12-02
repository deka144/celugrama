package com.ponc.service.impl;

import com.ponc.model.Cell;
import com.ponc.repo.ICellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.ICellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CellServiceImpl extends CRUDImpl<Cell,Integer> implements ICellService {
    private final ICellRepo repo;
    @Override
    protected IGenericRepo<Cell, Integer> getRepo() {
        return repo;
    }
}

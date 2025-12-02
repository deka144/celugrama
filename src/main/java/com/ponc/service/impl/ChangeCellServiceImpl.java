package com.ponc.service.impl;

import com.ponc.model.ChangeCell;
import com.ponc.repo.IChangeCellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IChangeCellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChangeCellServiceImpl extends CRUDImpl<ChangeCell, Integer> implements IChangeCellService {

    private final IChangeCellRepo repo;

    @Override
    protected IGenericRepo<ChangeCell, Integer> getRepo() {
        return repo;
    }
}

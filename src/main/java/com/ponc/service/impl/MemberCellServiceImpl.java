package com.ponc.service.impl;

import com.ponc.model.MemberCell;
import com.ponc.repo.IMemberCellRepo;
import com.ponc.repo.IGenericRepo;
import com.ponc.service.IMemberCellService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberCellServiceImpl extends CRUDImpl<MemberCell,Integer> implements IMemberCellService {

    private final IMemberCellRepo repo;

    @Override
    protected IGenericRepo<MemberCell, Integer> getRepo() {
        return null;
    }
}

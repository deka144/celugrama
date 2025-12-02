package com.ponc.service.impl;

import com.ponc.model.HistoryPerson;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IHistoryPersonRepo;
import com.ponc.service.IHistoryPersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryPersonServiceImpl extends CRUDImpl<HistoryPerson,Integer> implements IHistoryPersonService {

    private final IHistoryPersonRepo repo;

    @Override
    protected IGenericRepo<HistoryPerson, Integer> getRepo() {
        return repo;
    }
}

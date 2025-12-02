package com.ponc.service.impl;

import com.ponc.model.Period;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IPeriodRepo;
import com.ponc.service.IPeriodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PeriodServiceImpl extends  CRUDImpl<Period, Integer> implements IPeriodService {
    private final IPeriodRepo repo;

    @Override
    protected IGenericRepo<Period, Integer> getRepo() {
        return repo;
    }
}

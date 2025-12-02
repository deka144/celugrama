package com.ponc.service.impl;

import com.ponc.model.SpiritualGrowth;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.ISpiritualGrowthRepo;
import com.ponc.service.ISpiritualGrowthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpiritualGrowthServiceImpl extends CRUDImpl<SpiritualGrowth, Integer> implements ISpiritualGrowthService {
    private final ISpiritualGrowthRepo repo;

    @Override
    protected IGenericRepo<SpiritualGrowth, Integer> getRepo() {
        return repo;
    }
}

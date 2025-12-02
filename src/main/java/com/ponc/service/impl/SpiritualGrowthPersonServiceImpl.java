package com.ponc.service.impl;

import com.ponc.model.SpiritualGrowthPerson;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.ISpiritualGrowthPersonRepo;
import com.ponc.service.ISpiritualGrowthPersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpiritualGrowthPersonServiceImpl extends CRUDImpl<SpiritualGrowthPerson, Integer> implements ISpiritualGrowthPersonService {
    private final ISpiritualGrowthPersonRepo repo;

    @Override
    protected IGenericRepo<SpiritualGrowthPerson, Integer> getRepo() {
        return repo;
    }
}

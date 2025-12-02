package com.ponc.service.impl;

import com.ponc.model.Person;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IPersonRepo;
import com.ponc.service.IPersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonServiceImpl extends CRUDImpl<Person, Integer> implements IPersonService {
    private final IPersonRepo repo;
    @Override
    protected IGenericRepo<Person, Integer> getRepo() {
        return repo;
    }
}

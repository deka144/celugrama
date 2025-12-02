package com.ponc.service.impl;

import com.ponc.model.Guest;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IGuestRepo;
import com.ponc.service.IGuestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestServiceImpl extends CRUDImpl<Guest, Integer> implements IGuestService {

    private final IGuestRepo repo;
    @Override
    protected IGenericRepo<Guest, Integer> getRepo() {
        return repo;
    }
}

package com.ponc.service.impl;


import com.ponc.model.Menu;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IMenuRepo;
import com.ponc.service.IMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuServiceImpl extends CRUDImpl<Menu, Integer> implements IMenuService {

    private final IMenuRepo repo;
    @Override
    protected IGenericRepo<Menu, Integer> getRepo() {
        return repo;
    }
}

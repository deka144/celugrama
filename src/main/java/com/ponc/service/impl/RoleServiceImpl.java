package com.ponc.service.impl;

import com.ponc.model.Role;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IRoleRepo;
import com.ponc.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    private final IRoleRepo repo;

    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
        return repo;
    }
}

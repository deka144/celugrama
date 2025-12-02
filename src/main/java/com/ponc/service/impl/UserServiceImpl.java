package com.ponc.service.impl;

import com.ponc.model.User;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IUserRepo;
import com.ponc.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {
    private final IUserRepo repo;

    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return null;
    }
}

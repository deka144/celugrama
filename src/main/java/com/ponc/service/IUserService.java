package com.ponc.service;

import com.ponc.model.User;

public interface IUserService extends ICRUD<User,Integer>{
    User changeState(Integer id, Boolean state); // cambia solo el estado (state) del usuario y devuelve el registro ya guardado
}

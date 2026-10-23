package com.ponc.service;

import com.ponc.model.Role;

public interface IRoleService extends ICRUD<Role,Integer>{
    Role changeState(Integer id, Boolean state); // cambia solo el estado (state) del rol y devuelve el registro ya guardado
}

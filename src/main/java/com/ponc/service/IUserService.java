package com.ponc.service;

import com.ponc.model.User;
import org.springframework.data.domain.Page;

public interface IUserService extends ICRUD<User,Integer>{
    User changeState(Integer id, Boolean state); // cambia solo el estado (state) del usuario y devuelve el registro ya guardado

    Page<User> findPage(String search, int page, int size); // PAGINADO: busca usuarios (search puede ir vacío) y devuelve la página pedida
}

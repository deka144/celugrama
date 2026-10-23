package com.ponc.service;

import com.ponc.model.Menu;

public interface IMenuService extends ICRUD<Menu,Integer>{
    Menu changeState(Integer id, Boolean state); // cambia solo el estado (state) del menú y devuelve el registro ya guardado
}

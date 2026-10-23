package com.ponc.service;

import com.ponc.model.Person;

public interface IPersonService extends ICRUD<Person,Integer>{
    Person changeState(Integer id, Boolean state);
    // cambia solo el estado (state) de la persona y devuelve el registro ya guardado
}

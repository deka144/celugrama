package com.ponc.service;

import com.ponc.model.Person;
import org.springframework.data.domain.Page;

public interface IPersonService extends ICRUD<Person,Integer>{
    Person changeState(Integer id, Boolean state);
    // cambia solo el estado (state) de la persona y devuelve el registro ya guardado

    Page<Person> findPage(String search, int page, int size);
    // PAGINADO: busca personas (search puede ir vacío) y devuelve la página pedida
}

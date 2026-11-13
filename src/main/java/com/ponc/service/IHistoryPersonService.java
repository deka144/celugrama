package com.ponc.service;

import com.ponc.model.HistoryPerson;
import org.springframework.data.domain.Page;

public interface IHistoryPersonService extends ICRUD<HistoryPerson,Integer>{

    Page<HistoryPerson> findPage(String search, int page, int size);
    // PAGINADO: busca historiales de persona (search puede ir vacío) y devuelve la página pedida
}

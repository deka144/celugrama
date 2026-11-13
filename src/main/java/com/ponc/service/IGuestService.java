package com.ponc.service;

import com.ponc.model.Guest;
import org.springframework.data.domain.Page;


public interface IGuestService extends ICRUD<Guest,Integer>{
    Page<Guest> findPage(String search, int page, int size); // PAGINADO: busca invitados (search puede ir vacío) y devuelve la página pedida
}

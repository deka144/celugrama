package com.ponc.service;

import com.ponc.model.SpiritualGrowth;
import org.springframework.data.domain.Page;

public interface ISpiritualGrowthService extends ICRUD<SpiritualGrowth,Integer>{
    SpiritualGrowth changeState(Integer id, Boolean state);
    // cambia solo el estado (state) del crecimiento espiritual y devuelve el registro ya guardado

    Page<SpiritualGrowth> findPage(String search, int page, int size);
    // PAGINADO: busca crecimientos espirituales (search puede ir vacío) y devuelve la página pedida
}

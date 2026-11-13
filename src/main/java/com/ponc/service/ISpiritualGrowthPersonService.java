package com.ponc.service;

import com.ponc.model.SpiritualGrowthPerson;
import org.springframework.data.domain.Page;

public interface ISpiritualGrowthPersonService extends ICRUD<SpiritualGrowthPerson,Integer>{
    Page<SpiritualGrowthPerson> findPage(String search, int page, int size);
    // PAGINADO: busca crecimientos espirituales de personas (search puede ir vacío) y devuelve la página pedida
}

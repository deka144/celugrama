package com.ponc.service;

import com.ponc.model.SpiritualGrowth;

public interface ISpiritualGrowthService extends ICRUD<SpiritualGrowth,Integer>{
    SpiritualGrowth changeState(Integer id, Boolean state);
    // cambia solo el estado (state) del crecimiento espiritual y devuelve el registro ya guardado
}

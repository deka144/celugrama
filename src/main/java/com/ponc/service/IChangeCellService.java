package com.ponc.service;

import com.ponc.model.ChangeCell;

public interface IChangeCellService extends ICRUD<ChangeCell,Integer>{

    //Punto 8 : orquesta el cambio de celula de un Discipulo
    ChangeCell changeCell(Integer idMemberCellOld, Integer idCellNew);

    // Punto 12: orquesta el cambio de Lider de una celula.
    ChangeCell changeCellLeader(Integer idMemberCellOldLeader, Integer idPersonNewLeader);

}

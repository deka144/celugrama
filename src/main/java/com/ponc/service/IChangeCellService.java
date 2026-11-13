package com.ponc.service;

import com.ponc.model.ChangeCell;
import org.springframework.data.domain.Page;

public interface IChangeCellService extends ICRUD<ChangeCell,Integer>{

    //Punto 8 : orquesta el cambio de celula de un Discipulo
    ChangeCell changeCell(Integer idMemberCellOld, Integer idCellNew);

    // Punto 12: orquesta el cambio de Lider de una celula.
    ChangeCell changeCellLeader(Integer idMemberCellOldLeader, Integer idPersonNewLeader);

    Page<ChangeCell> findPage(String search, int page, int size);
    // PAGINADO: busca cambios de célula (search puede ir vacío) y devuelve la página pedida

}

package com.ponc.service;

import com.ponc.dto.LeaderCellResponseDTO;// DTO de salida con los datos de cada celula del Lider
import com.ponc.model.MemberCell;

import java.util.List;

public interface IMemberCellService extends ICRUD<MemberCell,Integer>{
    // Requerimiento 15: lista de celulas activas donde una persona
    // es Lider, con tipo, dia y hora -- para que el Lider elija.
    List<LeaderCellResponseDTO> findLeaderCells(Integer idPerson); // idPerson: la persona de la que se quiere la lista

}

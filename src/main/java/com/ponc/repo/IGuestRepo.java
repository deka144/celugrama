package com.ponc.repo;

import com.ponc.model.Guest;

import java.util.List;

public interface IGuestRepo extends IGenericRepo<Guest,Integer> {
    // Punto 6: un invitado solo puede registrar asistencia en UNA
    // celula por periodo (sin importar cual).
    boolean existsByPerson_IdPersonAndPeriod_IdPeriod(Integer idPerson, Integer idPeriod);

    // Punto 7: historial del invitado en una celula especifica,
    // ordenado del periodo mas viejo al mas nuevo, para revisar si
    // los ultimos 5 son consecutivos.
    List<Guest> findByPerson_IdPersonAndCell_IdCellOrderByPeriod_StartDateAsc(Integer idPerson, Integer idCell);


}

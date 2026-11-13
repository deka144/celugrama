package com.ponc.service;

import com.ponc.dto.DiscipleshipCompositionResponseDTO;
import com.ponc.model.Cell;
import org.springframework.data.domain.Page;

public interface ICellService extends ICRUD<Cell,Integer>{

        // Punto 13: verifica si una celula de tipo Discipulado sigue
        // cumpliendo el minimo de 6 Discipulos que ademas sean Lideres
        // activos en otra celula (reporte para la notificacion).
        DiscipleshipCompositionResponseDTO verifyDiscipleshipComposition(Integer idCell);

        Cell changeState(Integer id, Boolean state); // cambia solo el estado (state) de la célula y devuelve el registro ya guardado

        Page<Cell> findPage(String search, int page, int size);
        // PAGINADO: busca células (search puede ir vacío) y devuelve la página pedida
    }



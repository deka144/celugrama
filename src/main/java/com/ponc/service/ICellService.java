package com.ponc.service;

import com.ponc.dto.DiscipleshipCompositionResponseDTO;
import com.ponc.model.Cell;

public interface ICellService extends ICRUD<Cell,Integer>{

        // Punto 13: verifica si una celula de tipo Discipulado sigue
        // cumpliendo el minimo de 6 Discipulos que ademas sean Lideres
        // activos en otra celula (reporte para la notificacion).
        DiscipleshipCompositionResponseDTO verifyDiscipleshipComposition(Integer idCell);
    }



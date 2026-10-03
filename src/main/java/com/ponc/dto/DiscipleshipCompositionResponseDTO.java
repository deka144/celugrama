package com.ponc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de respuesta nuevo para el punto 13: resultado del reporte de
// composicion de una celula de tipo Discipulado. Lo consulta el
// frontend despues de un cambio de Lider (punto 12) o del cierre
// de una celula, para avisar si quedo por debajo del minimo de 6.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscipleshipCompositionResponseDTO {

    private Integer idCell;
    private Long activeDisciples;
    private Long disciplesStillLeadingElsewhere;
    private Boolean meetsMinimum;
}


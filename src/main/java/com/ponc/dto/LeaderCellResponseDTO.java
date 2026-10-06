package com.ponc.dto;

import com.ponc.model.enums.CellType; // tipo de la celula (Discipulado o Evangelismo)
import com.ponc.model.enums.DayType; // dia de la celula
import lombok.AllArgsConstructor; // genera el constructor con todos los campos
import lombok.Data; // genera getters, setters, equals, hashCode y toString
import lombok.NoArgsConstructor; // genera el constructor vacio


@Data // getters y setters automaticos
@AllArgsConstructor // constructor con los 4 campos (el service lo usa)
@NoArgsConstructor // constructor vacio (lo pide Jackson al armar el JSON)
public class LeaderCellResponseDTO { // solo de salida: lo arma el service, nunca llega desde el cliente

    private Integer idCell; // id de la celula
    private CellType type; // Discipulado o Evangelismo
    private DayType day; // dia en que se reune (en espanol)
    private String hour; // hora de inicio, como texto ('6:00 p.m.')
}


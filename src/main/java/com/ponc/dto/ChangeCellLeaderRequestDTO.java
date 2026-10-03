package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO nuevo para el punto 12: el frontend manda el MemberCell del
// Lider viejo y la persona nueva -- el MemberCell nuevo lo crea el
// orquestador (ChangeCellServiceImpl.changeCellLeader()).
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeCellLeaderRequestDTO {

    @NotNull
    private Integer idMemberCellOldLeader;

    @NotNull
    private Integer idPersonNewLeader;
}

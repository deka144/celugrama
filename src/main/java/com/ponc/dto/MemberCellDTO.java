package com.ponc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCellDTO {

    private Integer idMemberCell;

    @NotNull
    private Integer idCell;

    @NotNull
    private Integer idPerson;

    @NotNull
    private String  TypeMemberCell;

    private Boolean state=true;

}

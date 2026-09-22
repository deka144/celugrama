package com.ponc.dto;

import com.ponc.model.enums.MemberType;
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

//    @NotNull
//    private String  TypeMemberCell;

    // Se respeta el nombre TypeMemberCell (con T mayuscula)
    // tal cual esta en tu DTO real, sin renombrarlo.
    @NotNull
    private MemberType TypeMemberCell;

    private Boolean state=true;

}

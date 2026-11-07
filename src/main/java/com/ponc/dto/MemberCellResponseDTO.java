package com.ponc.dto;

import com.ponc.model.enums.MemberType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCellResponseDTO {

    private Integer idMemberCell;

    private Integer idCell;

    private Integer idPerson;

    private MemberType TypeMemberCell;

    private Boolean state;


}

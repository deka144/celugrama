package com.ponc.dto;

import com.ponc.model.enums.MemberType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// No lleva idMemberCell ni state (nace activo y solo cambia con PATCH /{id}/state).
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío: lo necesita Jackson para leer el JSON (Lombok)
public class MemberCellRequestDTO { // cuerpo del POST y del PUT de Miembro de célula

    @NotNull
    private Integer idCell;

    @NotNull
    private Integer idPerson;

    @NotNull
    private MemberType typeMemberCell; // en el JSON sigue llamándose typeMemberCell
}

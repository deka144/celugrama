package com.ponc.repo;

import com.ponc.model.ChangeCell;

public interface IChangeCellRepo extends IGenericRepo<ChangeCell,Integer> {

    // Eliminar un miembro: ¿aparece como miembro anterior o como miembro nuevo en algún cambio de célula?
    boolean existsByMemberCellOld_IdMemberCell(Integer idMemberCell); // NUEVO
    boolean existsByMemberCellNew_IdMemberCell(Integer idMemberCell); // NUEVO

}

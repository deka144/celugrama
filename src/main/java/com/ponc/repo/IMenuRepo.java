package com.ponc.repo;

import com.ponc.model.Menu;

public interface IMenuRepo extends IGenericRepo<Menu,Integer> {
    boolean existsByRoles_IdRole(Integer idRole); // NUEVO: ¿algún menú tiene este rol? (para no eliminar el rol)
}

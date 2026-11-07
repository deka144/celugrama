package com.ponc.repo;

import com.ponc.model.User;

public interface IUserRepo extends IGenericRepo<User,Integer> {
    boolean existsByUsername(String username);
    // NUEVO: ¿ya hay un usuario con ese username? (para el POST)

    boolean existsByUsernameAndIdUserNot(String username, Integer idUser);
    // NUEVO: ¿lo tiene otro usuario distinto de este? (para el PUT)

    boolean existsByPerson_IdPerson(Integer idPerson);
    // NUEVO: ¿hay un usuario de esta persona? (para no eliminar la persona)

    boolean existsByRoles_IdRole(Integer idRole);
    // NUEVO: ¿algún usuario tiene este rol? (para no eliminar el rol)

}

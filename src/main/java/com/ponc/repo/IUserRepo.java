package com.ponc.repo;

import com.ponc.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepo extends IGenericRepo<User,Integer> {
    boolean existsByUsername(String username);
    // NUEVO: ¿ya hay un usuario con ese username? (para el POST)

    boolean existsByUsernameAndIdUserNot(String username, Integer idUser);
    // NUEVO: ¿lo tiene otro usuario distinto de este? (para el PUT)

    boolean existsByPerson_IdPerson(Integer idPerson);
    // NUEVO: ¿hay un usuario de esta persona? (para no eliminar la persona)

    boolean existsByRoles_IdRole(Integer idRole);
    // NUEVO: ¿algún usuario tiene este rol? (para no eliminar el rol)

    // PAGINADO: busca usuarios por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT u FROM User u " + // PAGINADO: trae los registros de usuarios (alias u)
            "LEFT JOIN u.person p " + // PAGINADO: une con la persona para poder buscar por sus datos (LEFT JOIN: no descarta los registros que no tengan esa relación)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre de usuario contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.paternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido paterno de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.maternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido materno de la persona contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.numberDoc) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el número de documento de la persona contiene el texto (sin importar mayúsculas)
            "ORDER BY u.idUser") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<User> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño

}

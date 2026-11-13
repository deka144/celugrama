package com.ponc.repo;

import com.ponc.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPersonRepo extends IGenericRepo<Person,Integer> {
    boolean existsByNumberDoc(String numberDoc);
    // NUEVO: ¿ya hay una persona con ese número de documento? (para el POST)

    boolean existsByNumberDocAndIdPersonNot(String numberDoc, Integer idPerson);
    // NUEVO: ¿lo tiene otra persona distinta de esta? (para el PUT)

    boolean existsByEmail(String email);
    // NUEVO: ¿ya hay una persona con ese correo? (para el POST)

    boolean existsByEmailAndIdPersonNot(String email, Integer idPerson);
    // NUEVO: ¿lo tiene otra persona distinta de esta? (para el PUT)

    // PAGINADO: busca personas por texto y devuelve una sola página. Con :search vacío no filtra y trae todo.
    @Query("SELECT p FROM Person p " + // PAGINADO: trae los registros de personas (alias p)
            "WHERE :search = '' " + // PAGINADO: si no se escribió texto, la condición se cumple y trae todo
            "OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el nombre contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.paternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido paterno contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.maternalSurname) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el apellido materno contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.numberDoc) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el número de documento contiene el texto (sin importar mayúsculas)
            "OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%')) " + // PAGINADO: o el correo contiene el texto (sin importar mayúsculas)
            "ORDER BY p.idPerson") // PAGINADO: orden fijo por id; sin él las páginas podrían repetir o saltarse registros
    Page<Person> search(@Param("search") String search, Pageable pageable); // PAGINADO: search = texto (ya sin espacios); pageable = qué página y de qué tamaño


}

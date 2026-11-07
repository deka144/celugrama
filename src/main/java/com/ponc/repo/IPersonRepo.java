package com.ponc.repo;

import com.ponc.model.Person;

public interface IPersonRepo extends IGenericRepo<Person,Integer> {
    boolean existsByNumberDoc(String numberDoc);
    // NUEVO: ¿ya hay una persona con ese número de documento? (para el POST)

    boolean existsByNumberDocAndIdPersonNot(String numberDoc, Integer idPerson);
    // NUEVO: ¿lo tiene otra persona distinta de esta? (para el PUT)

    boolean existsByEmail(String email);
    // NUEVO: ¿ya hay una persona con ese correo? (para el POST)

    boolean existsByEmailAndIdPersonNot(String email, Integer idPerson);
    // NUEVO: ¿lo tiene otra persona distinta de esta? (para el PUT)

}

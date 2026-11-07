package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Person;
import com.ponc.model.Person;
import com.ponc.repo.*;
import com.ponc.service.IPersonService;
import com.ponc.util.DeleteCheckUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl extends CRUDImpl<Person, Integer> implements IPersonService {
    private final IPersonRepo repo;
    private final IUserRepo userRepo; // NUEVO: para revisar si otro registro usa este
    private final IMemberCellRepo memberCellRepo; // NUEVO: para revisar si otro registro usa este
    private final IGuestRepo guestRepo; // NUEVO: para revisar si otro registro usa este
    private final ISpiritualGrowthPersonRepo spiritualGrowthPersonRepo; // NUEVO: para revisar si otro registro usa este


    @Override
    protected IGenericRepo<Person, Integer> getRepo() {
        return repo;
    }

    @Override
    public Person save(Person t) {
        try {
            validateUnique(t, null); // NUEVO: revisa que no se repita (null = registro nuevo, no hay id que excluir)
            return super.save(t);
        } catch (ApiException ex) {
            throw ex; // NUEVO: deja pasar el mensaje de duplicado (409) tal cual
        } catch (Exception ex) {
            throw new ApiException("Error al crear la Persona.");
        }
    }

    @Override
    public Person update(Person t, Integer id) {
        try {
            Person actual = super.findById(id); // NUEVO: busca el registro guardado (si el id no existe lanza el 404)
            t.setState(actual.getState()); // NUEVO: conserva el state que ya tiene; el PUT no lo cambia (solo PATCH /{id}/state)
            validateUnique(t, id); // NUEVO: revisa que no se repita, sin contar a este mismo registro (id)
            return super.update(t, id);
        } catch (ApiException ex) {
            throw ex; // NUEVO: deja pasar el mensaje de duplicado (409) tal cual
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar la Persona.");
        }
    }

    @Override
    public List<Person> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener las Personas.");
        }
    }

    @Override
    public Person findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar la Persona");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.findById(id); // NUEVO: si el id no existe lanza el 404, antes de revisar las relaciones
            validateNoRelations(id); // NUEVO: si otro registro lo usa, lanza el 409 con el motivo
            super.deleteById(id);
        } catch (ApiException ex) {
            throw ex; // NUEVO: deja pasar el mensaje 409 tal cual
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar la Persona.");
        }
    }

    @Override // implementa el método nuevo de IPersonService
    public Person changeState(Integer id, Boolean state) { // cambia solo el estado de la persona
        try { // cualquier error inesperado sale con un mensaje propio
            Person obj = super.findById(id); // busca la persona; si no existe lanza ModelNotFoundException (404)
            obj.setState(state); // cambia únicamente el atributo state
            return super.save(obj); // guarda directo en la base, sin pasar por las validaciones del save() de esta clase
        } catch (ModelNotFoundException ex) { // si el id no existe...
            throw ex; // ...deja el 404 tal como ya funciona
        } catch (Exception ex) { // si falla cualquier otra cosa...
            throw new ApiException("Error al cambiar el estado de la Persona."); // ...responde con un mensaje propio
        } // cierra el try/catch
    } // cierra el método

    // NUEVO: lanza 409 si el número de documento o el correo ya los tiene otra persona.
    // id = null en el POST (nadie se excluye); id = el de la URL en el PUT (así la persona puede conservar sus propios datos).
    private void validateUnique(Person t, Integer id) {
        boolean numberDocUsed = (id == null)
                ? repo.existsByNumberDoc(t.getNumberDoc())
                : repo.existsByNumberDocAndIdPersonNot(t.getNumberDoc(), id);
        if (numberDocUsed) {
            throw new ApiException("Ya existe una persona con ese número de documento.", HttpStatus.CONFLICT);
        }
        boolean emailUsed = (id == null)
                ? repo.existsByEmail(t.getEmail())
                : repo.existsByEmailAndIdPersonNot(t.getEmail(), id);
        if (emailUsed) {
            throw new ApiException("Ya existe una persona con ese correo.", HttpStatus.CONFLICT);
        }
    }

    // NUEVO: lanza 409 si otro registro usa esta persona. Cada motivo que encuentra se suma a la lista.
    private void validateNoRelations(Integer id) {
        List<String> reasons = new ArrayList<>();
        if (userRepo.existsByPerson_IdPerson(id)) {
            reasons.add("tiene un usuario asociado");
        }
        if (memberCellRepo.existsByPerson_IdPerson(id)) {
            reasons.add("es miembro de una célula");
        }
        if (guestRepo.existsByPerson_IdPerson(id)) {
            reasons.add("está registrada como invitado");
        }
        if (spiritualGrowthPersonRepo.existsByPerson_IdPerson(id)) {
            reasons.add("tiene crecimientos espirituales registrados");
        }
        DeleteCheckUtil.failIfRelated("la persona", reasons);
    }


}

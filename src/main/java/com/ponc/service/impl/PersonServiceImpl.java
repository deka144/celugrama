package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Person;
import com.ponc.model.Person;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IPersonRepo;
import com.ponc.service.IPersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl extends CRUDImpl<Person, Integer> implements IPersonService {
    private final IPersonRepo repo;
    @Override
    protected IGenericRepo<Person, Integer> getRepo() {
        return repo;
    }

    @Override
    public Person save(Person t) {
        try {
            return super.save(t);
        } catch (Exception ex) {
            throw new ApiException("Error al crear la Persona.");
        }
    }

    @Override
    public Person update(Person t, Integer id) {
        try {
            return super.update(t, id);
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
            super.deleteById(id);
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

}

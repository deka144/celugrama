package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.User;
import com.ponc.model.User;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IUserRepo;
import com.ponc.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {
    private final IUserRepo repo;

    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return repo;
    }

    @Override
    public User save(User t) {
        try {
            validateUnique(t, null); // NUEVO: revisa que no se repita (null = registro nuevo, no hay id que excluir)
            return super.save(t);
        } catch (ApiException ex) {
            throw ex; // NUEVO: deja pasar el mensaje de duplicado (409) tal cual
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Usuario.");
        }
    }

    @Override
    public User update(User t, Integer id) {
        try {
            User actual = super.findById(id); // NUEVO: busca el registro guardado (si el id no existe lanza el 404)
            t.setState(actual.getState()); // NUEVO: conserva el state que ya tiene; el PUT no lo cambia (solo PATCH /{id}/state)
            validateUnique(t, id); // NUEVO: revisa que no se repita, sin contar a este mismo registro (id)
            return super.update(t, id);
        } catch (ApiException ex) {
            throw ex; // NUEVO: deja pasar el mensaje de duplicado (409) tal cual
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el Usuario.");
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los Usuarios.");
        }
    }

    @Override
    public User findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Usuario.");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            super.deleteById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al eliminar el Usuario.");
        }
    }

    @Override // implementa el método nuevo de IUserService
    public User changeState(Integer id, Boolean state) { // cambia solo el estado del usuario
        try { // cualquier error inesperado sale con un mensaje propio
            User obj = super.findById(id); // busca el usuario; si no existe lanza ModelNotFoundException (404)
            obj.setState(state); // cambia únicamente el atributo state
            return super.save(obj); // guarda directo en la base, sin pasar por las validaciones del save() de esta clase
        } catch (ModelNotFoundException ex) { // si el id no existe...
            throw ex; // ...deja el 404 tal como ya funciona
        } catch (Exception ex) { // si falla cualquier otra cosa...
            throw new ApiException("Error al cambiar el estado del Usuario."); // ...responde con un mensaje propio
        } // cierra el try/catch
    } // cierra el método


    // NUEVO: lanza 409 si el username ya lo tiene otro usuario.
    // id = null en el POST (nadie se excluye); id = el de la URL en el PUT (así el usuario puede conservar su propio username).
    private void validateUnique(User t, Integer id) {
        boolean usernameUsed = (id == null)
                ? repo.existsByUsername(t.getUsername())
                : repo.existsByUsernameAndIdUserNot(t.getUsername(), id);
        if (usernameUsed) {
            throw new ApiException("Ya existe un usuario con ese username.", HttpStatus.CONFLICT);
        }
    }


}

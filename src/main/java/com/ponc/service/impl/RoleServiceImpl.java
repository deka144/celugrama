package com.ponc.service.impl;

import com.ponc.exception.ApiException;
import com.ponc.exception.ModelNotFoundException;
import com.ponc.model.Role;
import com.ponc.model.Role;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IMenuRepo;
import com.ponc.repo.IRoleRepo;
import com.ponc.repo.IUserRepo;
import com.ponc.service.IRoleService;
import com.ponc.util.DeleteCheckUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    private final IRoleRepo repo;
    private final IUserRepo userRepo; // NUEVO: para revisar si otro registro usa este
    private final IMenuRepo menuRepo; // NUEVO: para revisar si otro registro usa este


    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
        return repo;
    }


    @Override
    public Role save(Role t) {
        try {
            return super.save(t);
        } catch (Exception ex) {
            throw new ApiException("Error al crear el Role.");
        }
    }

    @Override
    public Role update(Role t, Integer id) {
        try {
            Role actual = super.findById(id); // NUEVO: busca el registro guardado (si el id no existe lanza el 404)
            t.setState(actual.getState()); // NUEVO: conserva el state que ya tiene; el PUT no lo cambia (solo PATCH /{id}/state)
            return super.update(t, id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al actualizar el Rol.");
        }
    }

    @Override
    public List<Role> findAll() {
        try {
            return super.findAll();
        } catch (Exception ex) {
            throw new ApiException("Error al obtener los Roles.");
        }
    }

    @Override
    public Role findById(Integer id) {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw ex; // deja el 404 tal como ya funciona
        } catch (Exception ex) {
            throw new ApiException("Error al buscar el Rol.");
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
            throw new ApiException("Error al eliminar el Rol.");
        }
    }

    @Override // implementa el método nuevo de IRoleService
    public Role changeState(Integer id, Boolean state) { // cambia solo el estado del rol
        try { // cualquier error inesperado sale con un mensaje propio
            Role obj = super.findById(id); // busca el rol; si no existe lanza ModelNotFoundException (404)
            obj.setState(state); // cambia únicamente el atributo state
            return super.save(obj); // guarda directo en la base, sin pasar por las validaciones del save() de esta clase
        } catch (ModelNotFoundException ex) { // si el id no existe...
            throw ex; // ...deja el 404 tal como ya funciona
        } catch (Exception ex) { // si falla cualquier otra cosa...
            throw new ApiException("Error al cambiar el estado del Rol."); // ...responde con un mensaje propio
        } // cierra el try/catch
    } // cierra el método

    // NUEVO: lanza 409 si otro registro usa este rol. Cada motivo que encuentra se suma a la lista.
    private void validateNoRelations(Integer id) {
        List<String> reasons = new ArrayList<>();
        if (userRepo.existsByRoles_IdRole(id)) {
            reasons.add("está asignado a uno o más usuarios");
        }
        if (menuRepo.existsByRoles_IdRole(id)) {
            reasons.add("está asignado a uno o más menús");
        }
        DeleteCheckUtil.failIfRelated("el rol", reasons);
    }


}

package com.ponc.util;

import com.ponc.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;

// Arma y lanza el mensaje 409 cuando un registro no se puede eliminar porque otros registros lo usan.
public class DeleteCheckUtil {

    private DeleteCheckUtil() { // clase de utilidad: no se crean objetos de ella
    }

    // entityName: cómo se nombra el registro en el mensaje ("la persona", "la célula"...).
    // reasons: los motivos encontrados ("tiene un usuario asociado"...). Si la lista está vacía, no hace nada y el borrado sigue.
    public static void failIfRelated(String entityName, List<String> reasons) {
        if (reasons.isEmpty()) {
            return; // nadie lo usa: se puede eliminar
        }
        String joined = reasons.size() == 1
                ? reasons.get(0)
                : String.join(", ", reasons.subList(0, reasons.size() - 1)) + " y " + reasons.get(reasons.size() - 1); // "a, b y c"
        throw new ApiException("No se puede eliminar " + entityName + " porque " + joined + ".", HttpStatus.CONFLICT);
    }
}


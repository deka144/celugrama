package com.ponc.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

// Prepara los datos de búsqueda y de paginación que reciben los 11 services.
public final class PageUtil { // final y con constructor privado: solo tiene métodos estáticos,
                                                                        // no se crea con new

    private static final int MAX_SIZE = 100; // tope de registros por página, para que nadie
                                                                    // pida miles de una vez

    private PageUtil() { } // evita crear objetos de esta clase

    // Texto de búsqueda listo para la consulta: sin espacios al inicio ni al final; si no llegó nada, vacío.
    public static String text(String search) { // search viene del parámetro de la URL y puede ser null
        return search == null ? "" : search.trim(); // null pasa a "" (la consulta lo toma como "traer todo")
    }

    // Arma la paginación corrigiendo valores inválidos en lugar de fallar.
    public static Pageable pageable(int page, int size) { // page empieza en 0; size = registros por página
        int safePage = Math.max(page, 0); // una página negativa se toma como la 0
        int safeSize = Math.min(Math.max(size, 1), MAX_SIZE); // el tamaño queda entre 1 y 100
        return PageRequest.of(safePage, safeSize); // el orden lo define el ORDER BY de cada consulta
    }
}


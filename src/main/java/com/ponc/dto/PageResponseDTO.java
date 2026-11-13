package com.ponc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

// Página de resultados que se devuelve al front en los endpoints /page.
@Data // getters, setters, equals, hashCode y toString (Lombok)
@AllArgsConstructor // constructor con todos los campos (Lombok)
@NoArgsConstructor // constructor vacío (Lombok)
public class PageResponseDTO<T> { // T = el DTO de salida de cada entidad
                                // (PersonResponseDTO, CellResponseDTO...)

    private List<T> content; // los registros de esta página (como máximo "size")
    private long totalElements; // total de registros que cumplen la búsqueda, en todas las páginas
    private int totalPages; // cuántas páginas hay con el tamaño pedido
    private int page; // número de esta página (la primera es 0)
    private int size; // tamaño de página que se usó

    // Convierte la Page de Spring Data en este DTO.
    public static <T> PageResponseDTO<T> from(Page<T> p) { // recibe la página ya convertida a DTO
        return new PageResponseDTO<>( // arma el DTO con los datos de la página
                p.getContent(), // los registros de la página
                p.getTotalElements(), // el total de registros
                p.getTotalPages(), // el total de páginas
                p.getNumber(), // el número de esta página
                p.getSize()); // el tamaño de página
    }
}

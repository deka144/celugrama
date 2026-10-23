package com.ponc.dto;

import jakarta.validation.groups.Default;// grupo estándar de Bean Validation: las reglas normales (@NotNull, etc.)

public interface OnUpdate extends Default {
    // grupo 'al editar': incluye todas las reglas normales y además las que se marcan solo para el PUT
}

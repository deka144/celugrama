package com.ponc.model.enums;

import java.time.DayOfWeek;



import java.time.DayOfWeek; // el tipo de java.time con el que se compara el dia actual

public enum DayType { // los 7 dias de la semana, en espanol
    LUNES("lunes", DayOfWeek.MONDAY), // nombre del enum, etiqueta para mensajes y dia equivalente de java.time
    MARTES("martes", DayOfWeek.TUESDAY), // idem: nombre, etiqueta y dia de java.time
    MIERCOLES("miércoles", DayOfWeek.WEDNESDAY),  // Miércoles (un enum de Java no admite tildes)
    JUEVES("jueves", DayOfWeek.THURSDAY), // idem
    VIERNES("viernes", DayOfWeek.FRIDAY), // idem
    SABADO("sábado", DayOfWeek.SATURDAY),         // Sábado
    DOMINGO("domingo", DayOfWeek.SUNDAY); // el punto y coma cierra la lista de constantes

    private final String label; // el dia en espanol con tilde, solo para mensajes
    private final DayOfWeek dayOfWeek; // el dia equivalente en java.time

    DayType(String label, DayOfWeek dayOfWeek) { // constructor: Java lo llama una vez por cada constante
        this.label = label; // guarda la etiqueta
        this.dayOfWeek = dayOfWeek; // guarda el equivalente de java.time
    }

    // Nombre en espanol con tilde, solo para mensajes de error.
    public String getLabel() { // devuelve 'miércoles', 'sábado', etc.
        return label; // la etiqueta guardada
    }

    // Equivalente de java.time, para comparar con el dia actual.
    public DayOfWeek toDayOfWeek() { // convierte este enum al tipo de java.time
        return dayOfWeek; // el equivalente guardado
    }
}

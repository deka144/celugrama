package com.ponc.service.impl;

import com.ponc.model.Period;
import com.ponc.repo.IGenericRepo;
import com.ponc.repo.IPeriodRepo;
import com.ponc.service.IPeriodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;              // para identificar el dia "LUNES"
import java.time.LocalDate;              // para calcular fechas sin hora
import java.time.format.TextStyle;       // para pedir el nombre del mes completo
import java.time.temporal.TemporalAdjusters; // para encontrar "el proximo lunes"

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class PeriodServiceImpl extends  CRUDImpl<Period, Integer> implements IPeriodService {
    private final IPeriodRepo repo;

    // Locale espanol: hace que getDisplayName() devuelva "septiembre" en vez de "september"
    private static final Locale ES = new Locale("es", "ES");

    @Override
    protected IGenericRepo<Period, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Period> generarPeriodosDelMesActual() {
        List<Period> creados = new ArrayList<>(); // periodos que se van creando

        // Paso 1: primer dia del mes actual ("firstdate" en el procedimiento SQL)
        LocalDate primerDiaMes = LocalDate.now().withDayOfMonth(1);

        // Paso 2: primer lunes desde ese dia (reemplaza el while(dia!='lunes') del SQL)
        LocalDate primerLunes = primerDiaMes.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

        int mes = primerLunes.getMonthValue();
        int anio = primerLunes.getYear();

        // Paso 3: si ya existen periodos para este mes/anio, no se vuelve a generar
        // (reemplaza el "if not exists(...)" del SQL)
        if (repo.existsByMonthsAndYears(mes, anio)) {
            return creados; // vacio: no habia nada nuevo que crear
        }

        // Nombre del mes en espanol, con la primera letra en mayuscula
        String nombreMes = capitalizar(primerLunes.getMonth().getDisplayName(TextStyle.FULL, ES));

        // Paso 4: recorre semana por semana mientras siga dentro del mismo mes
        // (reemplaza el "while(mes2=mes1)" del SQL)
        LocalDate inicioSemana = primerLunes;
        int semana = 0;
        while (inicioSemana.getMonthValue() == mes) {
            LocalDate finSemana = inicioSemana.plusDays(6); // semana de 7 dias
            semana++;

            Period periodo = new Period();
            periodo.setStartDate(inicioSemana.atStartOfDay());
            periodo.setEndDate(finSemana.atStartOfDay());
            periodo.setMonths(mes);
            periodo.setYears(anio);
            periodo.setWeek(semana);
            periodo.setMonthtxt(nombreMes);
            periodo.setTheme1("sin definir"); // mismo valor por defecto que el SQL
            periodo.setTheme2("sin definir");

            creados.add(repo.save(periodo)); // guarda y agrega a la lista de retorno

            inicioSemana = inicioSemana.plusDays(7); // salta a la siguiente semana
        }
        return creados;
    }

    // Helper: pone en mayuscula la primera letra (java.time la devuelve en minusculas)
    private String capitalizar(String texto) {
        return texto.substring(0, 1).toUpperCase(ES) + texto.substring(1);
    }

}

package com.ponc.util;


import com.ponc.exception.ApiException;
import com.ponc.model.Cell;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

// Utilidad compartida por AttendanceServiceImpl y GuestServiceImpl
// (punto 10): valida que un registro se este haciendo el mismo dia
// de la semana en que se realiza la celula, y dentro de una ventana
// de 4 horas desde su hora de inicio.
public class CellScheduleUtil {

    private static final int WINDOW_HOURS = 4;

    // Cell.day guarda el dia en texto libre y con mayusculas
    // inconsistentes en la data real ('viernes', 'Jueves', 'Sabado').
    // Se normaliza a minusculas y sin acentos antes de comparar.
    private static final Map<String, DayOfWeek> DAYS = Map.of(
            "lunes", DayOfWeek.MONDAY,
            "martes", DayOfWeek.TUESDAY,
            "miercoles", DayOfWeek.WEDNESDAY,
            "jueves", DayOfWeek.THURSDAY,
            "viernes", DayOfWeek.FRIDAY,
            "sabado", DayOfWeek.SATURDAY,
            "domingo", DayOfWeek.SUNDAY
    );

    public static void validateRegistrationWithinSchedule(Cell cell) {
        DayOfWeek cellDay = parseDay(cell.getDay());
        LocalTime cellTime = parseTime(cell.getHour());

        LocalDateTime now = LocalDateTime.now();
        if (now.getDayOfWeek() != cellDay) {
            throw new ApiException("Solo se puede registrar el día que se realiza la célula (" + cell.getDay() + ").");
        }

        LocalDateTime start = LocalDateTime.of(now.toLocalDate(), cellTime);
        LocalDateTime limit = start.plusHours(WINDOW_HOURS);

        if (now.isBefore(start)) {
            throw new ApiException("Todavía no es la hora de la célula (empieza a las " + cell.getHour() + ").");
        }
        if (now.isAfter(limit)) {
            throw new ApiException("Ya pasó la ventana de " + WINDOW_HOURS + " horas para registrar esta célula.");
        }
    }

    private static DayOfWeek parseDay(String dayText) {
        DayOfWeek day = DAYS.get(normalize(dayText));
        if (day == null) {
            throw new ApiException("El día de la célula (\"" + dayText + "\") no se reconoce.");
        }
        return day;
    }

    // Cell.hour guarda la hora como texto tipo "6:00 p.m." -- se parsea
    // a mano en vez de usar DateTimeFormatter porque el formato con
    // puntos ("p.m.") no calza bien con los patrones de Locale.
    private static LocalTime parseTime(String timeText) {
        try {
            String cleaned = timeText.trim().toLowerCase().replace(".", "").replace(" ", "");
            boolean isPm = cleaned.endsWith("pm");
            boolean isAm = cleaned.endsWith("am");
            if (!isPm && !isAm) {
                throw new IllegalArgumentException("falta am/pm");
            }
            String timeOnly = cleaned.replace("pm", "").replace("am", "");
            String[] parts = timeOnly.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            if (isPm && hour != 12) hour += 12;
            if (isAm && hour == 12) hour = 0;
            return LocalTime.of(hour, minute);
        } catch (Exception ex) {
            throw new ApiException("La hora de la célula (\"" + timeText + "\") no se pudo interpretar.");
        }
    }

    private static String normalize(String text) {
        return text.trim().toLowerCase()
                .replace("á", "a").replace("é", "e").replace("í", "i")
                .replace("ó", "o").replace("ú", "u");
    }
}

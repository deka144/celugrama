package com.ponc.util;


import com.ponc.exception.ApiException;
import com.ponc.model.Cell;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;


// Utilidad compartida por AttendanceServiceImpl y GuestServiceImpl
// (punto 10): valida que un registro se este haciendo el mismo dia
// de la semana en que se realiza la celula, y dentro de una ventana
// de 4 horas desde su hora de inicio.
// Tambien la usa MemberCellServiceImpl (Requerimiento 15), que
// reutiliza parseTime() para comparar las horas de 2 celulas.
public class CellScheduleUtil {

    private static final int WINDOW_HOURS = 4;


    public static void validateRegistrationWithinSchedule(Cell cell) {
        DayOfWeek cellDay = cell.getDay().toDayOfWeek(); // antes: parseDay(cell.getDay())
        LocalTime cellTime = parseTime(cell.getHour());

        LocalDateTime now = LocalDateTime.now();
        if (now.getDayOfWeek() != cellDay) {
            throw new ApiException("Solo se puede registrar el día que se realiza la célula (" + cell.getDay().getLabel() + ").");
            // antes: cell.getDay() (texto); getLabel() da el dia en espanol
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

    // Cell.hour guarda la hora como texto tipo "6:00 p.m." -- se parsea
    // a mano en vez de usar DateTimeFormatter porque el formato con
    // puntos ("p.m.") no calza bien con los patrones de Locale.
    public static LocalTime parseTime(String timeText) { // antes: private; ahora public porque MemberCellServiceImpl (Requerimiento 15) lo usa
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

}

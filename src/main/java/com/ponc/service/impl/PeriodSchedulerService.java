package com.ponc.service.impl;

import com.ponc.service.IPeriodService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PeriodSchedulerService {
    private final IPeriodService service;
    // Formato cron de Spring: segundo minuto hora dia-del-mes mes dia-de-la-semana
    // "0 0 0 1 * *" = 00:00 del dia 1, cualquier mes, cualquier dia de semana
    // Es decir: se ejecuta automaticamente a medianoche el dia 1 de cada mes
    @Scheduled(cron = "0 0 0 1 * *")
    public void ejecucionAutomaticaMensual() {
        service.generarPeriodosDelMesActual(); // reutiliza el mismo metodo que usa Swagger
    }
}



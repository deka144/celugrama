-- V5: convierte attendance.offering de texto (varchar) a numérico,
-- para que coincida con el DTO y se pueda sumar, comparar y ordenar.

-- Si alguna fila tiene un texto que no es un número (por ejemplo 'abc'),
-- esta migración falla y Flyway no aplica nada: revisa esos datos antes.

ALTER TABLE public.attendance -- la tabla de asistencias
    ALTER COLUMN offering TYPE numeric(12,2) -- de varchar(255) a número con 2 decimales
    USING offering::numeric(12,2); -- convierte cada valor existente ('150.00' pasa a 150.00)

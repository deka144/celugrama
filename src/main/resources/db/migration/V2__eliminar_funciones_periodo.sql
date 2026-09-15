-- Elimina los objetos PL/pgSQL que ya no se usan, ahora que
-- la generacion de periodos vive en Java (PeriodServiceImpl).

-- Se elimina primero el procedimiento, porque es el que llama a las otras dos funciones.
DROP PROCEDURE IF EXISTS public.periodos_mes();

-- Estas dos ya no las llama nadie mas en la base de datos.
DROP FUNCTION IF EXISTS public.nombre_mes();
DROP FUNCTION IF EXISTS public.nombre_dia(date);
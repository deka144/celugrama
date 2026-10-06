-- V4: convierte cell.day de texto libre (espanol, mayusculas
-- inconsistentes) al nombre de las constantes del enum DayType
-- (LUNES...DOMINGO), para poder usar @Enumerated(EnumType.STRING).

-- No hace falta ampliar la columna: cell.day ya era varchar
-- sin length explicito (por defecto da para mas de 20
-- caracteres), y "MIERCOLES" es el nombre mas largo (9).

UPDATE public.cell SET day = 'LUNES'     WHERE LOWER(day) IN ('lunes'); -- cualquier variante de mayusculas pasa a LUNES
UPDATE public.cell SET day = 'MARTES'    WHERE LOWER(day) IN ('martes'); -- pasa a MARTES
UPDATE public.cell SET day = 'MIERCOLES' WHERE LOWER(day) IN ('miercoles', 'miércoles'); -- con o sin tilde, pasa a MIERCOLES
UPDATE public.cell SET day = 'JUEVES'    WHERE LOWER(day) IN ('jueves'); -- pasa a JUEVES
UPDATE public.cell SET day = 'VIERNES'   WHERE LOWER(day) IN ('viernes'); -- pasa a VIERNES
UPDATE public.cell SET day = 'SABADO'    WHERE LOWER(day) IN ('sabado', 'sábado'); -- con o sin tilde, pasa a SABADO
UPDATE public.cell SET day = 'DOMINGO'   WHERE LOWER(day) IN ('domingo'); -- pasa a DOMINGO

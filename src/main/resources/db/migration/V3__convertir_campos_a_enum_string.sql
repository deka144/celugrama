-- V3: convierte 6 campos de codigo corto (String) a texto de
-- enum, para poder usar @Enumerated(EnumType.STRING) en las
-- entidades. Ver Pasos 1 y 2 de este manual para el codigo Java.

-- 1) Ampliar las columnas que hoy son varchar(1): con
--    EnumType.STRING, Hibernate guarda el NOMBRE completo del
--    enum ("MASCULINO", "DISCIPULADO"...) y eso no entra en
--    un solo caracter.
ALTER TABLE public.cell
    ALTER COLUMN grid TYPE character varying(20);
ALTER TABLE public.cell
    ALTER COLUMN type TYPE character varying(20);
ALTER TABLE public.person
    ALTER COLUMN genre TYPE character varying(20);
ALTER TABLE public.person
    ALTER COLUMN type_doc TYPE character varying(20);
ALTER TABLE public.person
    ALTER COLUMN civil_status TYPE character varying(20);
-- member_cell.type_member_cell ya es varchar(255): no hace
-- falta ampliarla.

-- 2) Cell.grid (Grid): convertir el codigo viejo al nombre
--    nuevo del enum.
UPDATE public.cell SET grid = 'DAMAS'   WHERE grid = 'd';
UPDATE public.cell SET grid = 'VARONES' WHERE grid = 'v';
UPDATE public.cell SET grid = 'NINOS'   WHERE grid = 'n';
UPDATE public.cell SET grid = 'ROCAS'   WHERE grid = 'r';
UPDATE public.cell SET grid = 'JOVENES' WHERE grid = 'j';

-- 3) Cell.type (CellType):
UPDATE public.cell SET type = 'EVANGELISMO' WHERE type = 'e';
UPDATE public.cell SET type = 'DISCIPULADO' WHERE type = 'd';

-- 4) Person.genre (Gender):
UPDATE public.person SET genre = 'MASCULINO' WHERE genre = 'M';
UPDATE public.person SET genre = 'FEMENINO'  WHERE genre = 'F';

-- 5) MemberCell.type_member_cell (MemberType):
-- OJO: tu data.sql actual comenta 'M' = miembro. Aqui asumo
-- que tu "miembro" es lo mismo que tu "Discipulo" (lo que
-- pediste). Si son dos roles distintos, avisame antes de
-- correr este UPDATE en tu base de datos real.
UPDATE public.member_cell
   SET type_member_cell = 'LIDER'
 WHERE type_member_cell = 'L';

UPDATE public.member_cell
   SET type_member_cell = 'DISCIPULO'
 WHERE type_member_cell = 'M';

-- 6) Person.type_doc (DocumentType): en tu data.sql real solo
-- aparece el codigo '1'. El resto (2, 3, 4) los uso segun
-- el emparejamiento que ya me confirmaste.
UPDATE public.person SET type_doc = 'DNI' WHERE type_doc = '1';
UPDATE public.person
   SET type_doc = 'CARNE_EXTRANJERIA'
 WHERE type_doc = '2';
UPDATE public.person SET type_doc = 'PASAPORTE'
 WHERE type_doc = '3';
UPDATE public.person SET type_doc = 'RUC' WHERE type_doc = '4';

-- 7) Person.civil_status (CivilStatus): en tu data.sql real
-- solo aparece el codigo 'c'. El resto (s, v, d, u) los uso
-- segun el emparejamiento que ya me confirmaste.
UPDATE public.person
   SET civil_status = 'CASADO'
 WHERE civil_status = 'c';
UPDATE public.person
   SET civil_status = 'SOLTERO'
 WHERE civil_status = 's';
UPDATE public.person
   SET civil_status = 'VIUDO'
 WHERE civil_status = 'v';
UPDATE public.person
   SET civil_status = 'DIVORCIADO'
 WHERE civil_status = 'd';
UPDATE public.person
   SET civil_status = 'CONVIVIENTE'
 WHERE civil_status = 'u';

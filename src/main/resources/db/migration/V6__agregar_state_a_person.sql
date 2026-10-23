-- true = persona activa, false = persona inactiva.
-- NOT NULL: toda persona debe tener un estado.
-- DEFAULT true: las personas que ya existen (y las que inserta data.sql sin indicar state) quedan activas.
ALTER TABLE public.person ADD COLUMN state boolean NOT NULL DEFAULT true;

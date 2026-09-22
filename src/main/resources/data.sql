-- data.sql - Celugrama MCI
-- Se ejecuta automaticamente en cada arranque de la app
-- (spring.sql.init.mode=always). Vacia la data anterior
-- y la vuelve a insertar, para que la base de datos quede
-- igual sin importar en que computadora corras el proyecto.

TRUNCATE TABLE
    attendance_detail, attendance, change_cell, guest,
    history_person, member_cell, menu_role, spiritual_growth_person,
    user_role, user_data, cell, period, person, role, menu,
    spiritual_growth
RESTART IDENTITY CASCADE;

-- Cell (data real)
INSERT INTO public.cell (id_cell, address, cellular, day, grid, hour, level, observations, open_date, phone, photo_url, reference, state, type) VALUES (1, 'cerquita nomas', '454545456', 'viernes', 'DAMAS', '6:00 p.m.', '12', 'niños', '2023-05-14 00:00:00', '614554', 'dfsfsffd', 'lejitos', true, 'EVANGELISMO');
INSERT INTO public.cell (id_cell, address, cellular, day, grid, hour, level, observations, open_date, phone, photo_url, reference, state, type) VALUES (2, 'cerquita nomas', '454545478', 'Jueves', 'VARONES', '8:00 p.m.', '12', 'Jovenes', '2023-05-14 00:00:00', '614567', 'dfsfsffd', 'lejitos', true, 'DISCIPULADO');
INSERT INTO public.cell (id_cell, address, cellular, day, grid, hour, level, observations, open_date, phone, photo_url, reference, state, type) VALUES (3, 'por aca nomas', '454545478', 'Viernes', 'NINOS', '7:00 p.m.', '144', 'Jovenes', '2024-07-21 00:00:00', '614523', 'dfsfsffd', 'por alla. nomas', true, 'EVANGELISMO');
INSERT INTO public.cell (id_cell, address, cellular, day, grid, hour, level, observations, open_date, phone, photo_url, reference, state, type) VALUES (4, 'lejasos', '454545111', 'Viernes', 'ROCAS', '8:00 p.m.', '1728', 'Jovenes', '2021-03-24 00:00:00', '614512', 'dfsfsffd', 'lejitos', true, 'DISCIPULADO');
INSERT INTO public.cell (id_cell, address, cellular, day, grid, hour, level, observations, open_date, phone, photo_url, reference, state, type) VALUES (5, 'mas alla', '454545999', 'Sabado', 'JOVENES', '5:00 p.m.', '99', 'Jovenes', '2022-09-10 00:00:00', '614599', 'dfsfsffd', 'por el parque', true, 'EVANGELISMO');

-- Period (data real)
INSERT INTO public.period (id_period, end_date, months, monthtxt, start_date, theme1, theme2, week, years) VALUES (2, '2025-08-17 00:00:00', 8, 'Agosto', '2025-08-11 00:00:00', 'sin definir', 'sin definir', 2, 2025);
INSERT INTO public.period (id_period, end_date, months, monthtxt, start_date, theme1, theme2, week, years) VALUES (3, '2025-08-24 00:00:00', 8, 'Agosto', '2025-08-18 00:00:00', 'sin definir', 'sin definir', 3, 2025);
INSERT INTO public.period (id_period, end_date, months, monthtxt, start_date, theme1, theme2, week, years) VALUES (4, '2025-08-31 00:00:00', 8, 'Agosto', '2025-08-25 00:00:00', 'sin definir', 'sin definir', 4, 2025);
INSERT INTO public.period (id_period, end_date, months, monthtxt, start_date, theme1, theme2, week, years) VALUES (1, '2025-08-10 00:00:00', 8, 'Agosto', '2025-08-04 00:00:00', 'tema1', 'tema2', 1, 2025);

-- Person (data real)
INSERT INTO public.person (id_person, address, birth_date, cellular, civil_status, email, genre, maternal_surname, name, number_doc, paternal_surname, phone, photo_url, reference, type_doc) VALUES (5, 'carretera antigua yarina 170', '1981-06-24 00:00:00', '94805245', 'CASADO', 'juanbomba@gmail.com', 'MASCULINO', 'Morales', 'Juan Carlos', '948052967', 'Ponce', '455667', 'sdssdssdsd', 'por real plaza', 'DNI');
INSERT INTO public.person (id_person, address, birth_date, cellular, civil_status, email, genre, maternal_surname, name, number_doc, paternal_surname, phone, photo_url, reference, type_doc) VALUES (4, 'carretera antigua yarina 170', '1979-03-26 00:00:00', '948052432', 'SOLTERO', 'paulofresh@gmail.com', 'MASCULINO', 'Morales', 'Paulo Cesar', '948052934', 'Ponce', '', 'sdssdssdsd', 'por real plaza', 'CARNE_EXTRANJERIA');
INSERT INTO public.person (id_person, address, birth_date, cellular, civil_status, email, genre, maternal_surname, name, number_doc, paternal_surname, phone, photo_url, reference, type_doc) VALUES (6, 'carretera antigua yarina 170', '1982-07-10 00:00:00', '94805245', 'DIVORCIADO', 'edercinsoft@gmail.com', 'MASCULINO', 'Morales', 'Edeher Rossetti', '45454545', 'Ponce', '455667', 'sdssdssdsd', 'por real plaza', 'PASAPORTE');
INSERT INTO public.person (id_person, address, birth_date, cellular, civil_status, email, genre, maternal_surname, name, number_doc, paternal_surname, phone, photo_url, reference, type_doc) VALUES (7, 'jr. las flores 250', '1985-11-02 00:00:00', '954812345', 'VIUDO', 'mariafernanda@gmail.com', 'FEMENINO', 'Rios', 'Maria Fernanda', '948055555', 'Vasquez', '455670', 'sdssdssdsd', 'por el mercado', 'RUC');
INSERT INTO public.person (id_person, address, birth_date, cellular, civil_status, email, genre, maternal_surname, name, number_doc, paternal_surname, phone, photo_url, reference, type_doc) VALUES (8, 'av. los pinos 340', '1990-04-18 00:00:00', '934567890', 'CONVIVIENTE', 'luciatorres@gmail.com', 'FEMENINO', 'Campos', 'Lucia Andrea', '948055566', 'Torres', '455680', 'sdssdssdsd', 'por el colegio', 'DNI');

-- Menu (data real)
INSERT INTO public.menu (id_menu, icon, name, state, url) VALUES (1, 'user', 'Guest', true, '/Guests');
INSERT INTO public.menu (id_menu, icon, name, state, url) VALUES (2, 'users', 'Person', true, '/Person');
INSERT INTO public.menu (id_menu, icon, name, state, url) VALUES (3, 'menu', 'Menu', true, '/menu');
INSERT INTO public.menu (id_menu, icon, name, state, url) VALUES (4, 'cell', 'Cel', true, '/cell');
INSERT INTO public.menu (id_menu, icon, name, state, url) VALUES (5, 'period', 'Periodo', true, '/period');

-- Spiritual growth (data real)
INSERT INTO public.spiritual_growth (id_spiritual_growth, description, state) VALUES (1, 'Encuentro', true);
INSERT INTO public.spiritual_growth (id_spiritual_growth, description, state) VALUES (2, 'Bautizo', true);
INSERT INTO public.spiritual_growth (id_spiritual_growth, description, state) VALUES (3, 'Escuela 1 Nivel', true);
INSERT INTO public.spiritual_growth (id_spiritual_growth, description, state) VALUES (4, 'Escuela 2 Nivel', true);
INSERT INTO public.spiritual_growth (id_spiritual_growth, description, state) VALUES (5, 'Escuela 3 Nivel', true);

-- ================================================
-- A partir de aqui: data INVENTADA para las tablas
-- que estaban vacias, solo para tener el flujo
-- completo de prueba.
-- ================================================

-- Role
INSERT INTO public.role (id_role, name, state) VALUES (1, 'Administrador', true);
INSERT INTO public.role (id_role, name, state) VALUES (2, 'Lider de celula', true);

-- User_data (depende de person). id_user NO es autoincremental
-- en esta tabla (no tiene IDENTITY en la BD), se asigna a mano.
-- La contraseña es texto plano de prueba, no usar en produccion.
INSERT INTO public.user_data (id_user, password, state, username, id_person) VALUES (1, 'test1234', true, 'pponce', 4);
INSERT INTO public.user_data (id_user, password, state, username, id_person) VALUES (2, 'test1234', true, 'eponce', 6);

-- User_role (tabla puente user_data + role, sin id propio)
INSERT INTO public.user_role (id_user, id_role) VALUES (1, 1);
INSERT INTO public.user_role (id_user, id_role) VALUES (2, 2);

-- Menu_role (tabla puente menu + role, sin id propio)
-- Administrador (role 1) ve los 5 menus; Lider (role 2) ve 3.
INSERT INTO public.menu_role (id_menu, id_role) VALUES (1, 1);
INSERT INTO public.menu_role (id_menu, id_role) VALUES (2, 1);
INSERT INTO public.menu_role (id_menu, id_role) VALUES (3, 1);
INSERT INTO public.menu_role (id_menu, id_role) VALUES (4, 1);
INSERT INTO public.menu_role (id_menu, id_role) VALUES (5, 1);
INSERT INTO public.menu_role (id_menu, id_role) VALUES (1, 2);
INSERT INTO public.menu_role (id_menu, id_role) VALUES (2, 2);
INSERT INTO public.menu_role (id_menu, id_role) VALUES (4, 2);

-- Member_cell (depende de cell + person)
-- type_member_cell: 'L' = lider, 'M' = miembro (ejemplo)
INSERT INTO public.member_cell (id_member_cell, state, type_member_cell, id_cell, id_person) VALUES (1, true, 'LIDER', 1, 4);
INSERT INTO public.member_cell (id_member_cell, state, type_member_cell, id_cell, id_person) VALUES (2, true, 'DISCIPULO', 1, 5);
INSERT INTO public.member_cell (id_member_cell, state, type_member_cell, id_cell, id_person) VALUES (3, true, 'LIDER', 2, 6);

-- Attendance (depende de cell via id_cel + period)
-- offering es varchar en esta tabla, no numerico
INSERT INTO public.attendance (id_attendance, offering, id_cel, id_period) VALUES (1, '150.00', 1, 1);
INSERT INTO public.attendance (id_attendance, offering, id_cel, id_period) VALUES (2, '200.50', 2, 1);

-- Attendance_detail (depende de attendance via id_atten + member_cell)
INSERT INTO public.attendance_detail (id_attendance_detail, attended, id_member_cell, id_atten) VALUES (1, true, 1, 1);
INSERT INTO public.attendance_detail (id_attendance_detail, attended, id_member_cell, id_atten) VALUES (2, true, 2, 1);
INSERT INTO public.attendance_detail (id_attendance_detail, attended, id_member_cell, id_atten) VALUES (3, false, 3, 2);

-- Change_cell (depende de member_cell, old y new)
INSERT INTO public.change_cell (id_change_cell, change_date, id_member_cell_new, id_member_cell_old) VALUES (1, '2025-01-15 00:00:00', 3, 2);

-- Guest (depende de cell + period + person)
INSERT INTO public.guest (id_guest, id_cell, id_period, id_person) VALUES (1, 3, 1, 5);
INSERT INTO public.guest (id_guest, id_cell, id_period, id_person) VALUES (2, 4, 2, 6);

-- Spiritual_growth_person (depende de person + spiritual_growth)
-- id_spiritual_growth_person tampoco es autoincremental.
INSERT INTO public.spiritual_growth_person (id_spiritual_growth_person, date, periodo, id_person, id_spiritual_growth) VALUES (1, '2024-03-10 00:00:00', 'Marzo 2024', 4, 1);
INSERT INTO public.spiritual_growth_person (id_spiritual_growth_person, date, periodo, id_person, id_spiritual_growth) VALUES (2, '2024-05-20 00:00:00', 'Mayo 2024', 5, 2);

-- History_person (tabla generica de auditoria, sin FK real:
-- id_table + name_table apuntan "a mano" a otra tabla)
INSERT INTO public.history_person (id_history_person, id_table, name_table, register_date) VALUES (1, 4, 'person', '2025-01-01 00:00:00');
INSERT INTO public.history_person (id_history_person, id_table, name_table, register_date) VALUES (2, 1, 'cell', '2025-01-02 00:00:00');

-- Ajusta las secuencias para que el proximo ID autogenerado
-- no choque con los IDs insertados arriba. (user_data y
-- spiritual_growth_person no tienen secuencia propia, por
-- eso no aparecen aqui).
SELECT pg_catalog.setval('public.cell_id_cell_seq', 5, true);
SELECT pg_catalog.setval('public.period_id_period_seq', 4, true);
SELECT pg_catalog.setval('public.person_id_person_seq', 8, true);
SELECT pg_catalog.setval('public.menu_id_menu_seq', 5, true);
SELECT pg_catalog.setval('public.spiritual_growth_id_spiritual_growth_seq', 5, true);
SELECT pg_catalog.setval('public.role_id_role_seq', 2, true);
SELECT pg_catalog.setval('public.member_cell_id_member_cell_seq', 3, true);
SELECT pg_catalog.setval('public.attendance_id_attendance_seq', 2, true);
SELECT pg_catalog.setval('public.attendance_detail_id_attendance_detail_seq', 3, true);
SELECT pg_catalog.setval('public.change_cell_id_change_cell_seq', 1, true);
SELECT pg_catalog.setval('public.guest_id_guest_seq', 2, true);
SELECT pg_catalog.setval('public.history_person_id_history_person_seq', 2, true);

INSERT INTO cuenta (id, fecha_alta, tipo_cuenta, saldo, mercado_pago_id, estado_cuenta, km_recorridos_mes_premium)
VALUES
    (2001, '2023-01-01', 'BASICA', 15000.0, 'mp_2001', 'ACTIVA', NULL),
    (2002, '2023-02-01', 'PREMIUM', 30000.0, 'mp_2002', 'ACTIVA', 5.0),
    (2003, '2023-03-01', 'BASICA', 18000.0, 'mp_2003', 'ACTIVA', NULL),
    (2004, '2023-04-01', 'PREMIUM', 25000.0, 'mp_2004', 'ACTIVA', 10.0),
    (2005, '2023-05-01', 'BASICA', 10000.0, 'mp_2005', 'ACTIVA', NULL),
    (2006, '2023-06-01', 'PREMIUM', 20000.0, 'mp_2006', 'ACTIVA', 7.0),
    (2007, '2023-07-01', 'BASICA', 12000.0, 'mp_2007', 'ACTIVA', NULL),
    (2008, '2023-08-01', 'PREMIUM', 22000.0, 'mp_2008', 'ACTIVA', 3.5),
    (2009, '2023-09-01', 'BASICA', 11000.0, 'mp_2009', 'ACTIVA', NULL),
    (2010, '2023-10-01', 'PREMIUM', 27000.0, 'mp_2010', 'ACTIVA', 12.0),
    (2011, '2023-11-01', 'BASICA', 13000.0, 'mp_2011', 'ACTIVA', NULL);


-- Asociamos la cuenta 2001 con dos usuarios que tienen viajes: 2001 y 2003.
INSERT INTO cuenta_usuarios_id (cuenta_id, usuarios) VALUES (2001, 2001);
INSERT INTO cuenta_usuarios_id (cuenta_id, usuarios) VALUES (2001, 2003);

INSERT INTO cuenta_usuarios_id (cuenta_id, usuarios) VALUES (2002, 2002);
INSERT INTO cuenta_usuarios_id (cuenta_id, usuarios) VALUES (2003, 2003);
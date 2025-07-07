INSERT INTO usuario (id, nombre, apellido, numero_celular, email, password, rol, latitud, longitud)
VALUES
    -- Usuario 1: Muy cerca de la Parada 1 (posX: 100, posY: 200).
    (2001, 'Juan', 'Perez', '1122334455', 'juan.perez@example.com', 'password123', 'USUARIO', 205.0, 105.0),
    -- Usuario 2: Entre Parada 2 (150, 250) y Parada 3 (200, 300)
    (2002, 'Ana', 'Gomez', '1133445566', 'ana.gomez@example.com', 'password123', 'USUARIO', 275.0, 175.0),
    -- Usuario 3: Cerca del centro del "mapa" de paradas
    (2003, 'Carlos', 'Rodriguez', '1144556677', 'carlos.rodriguez@example.com', 'password123', 'USUARIO', 370.0, 280.0),
    -- Usuario 4 (Admin): Cerca de Parada 6 (350, 450)
    (2004, 'Admin', 'Principal', '9999999999', 'admin@monopatin.com', 'adminpass', 'ADMIN', 455.0, 355.0),
    -- Usuario 5: En una esquina, lejos de la mayoría de las paradas
    (2005, 'Pedro', 'Martinez', '1166778899', 'pedro.martinez@example.com', 'password123', 'USUARIO', 150.0, 500.0),
    -- Usuario 6: Muy cerca de Parada 7 (400, 500)
    (2006, 'Sofia', 'Lopez', '1177889900', 'sofia.lopez@example.com', 'password123', 'USUARIO', 495.0, 405.0),
    -- Usuario 7: Cerca de Parada 3
    (2007, 'Sofia', 'Castro', '1177889901', 'sofia.castro@example.com', 'password123', 'USUARIO', 320.0, 220.0),
    -- Usuario 8: Cerca de Parada 8 (450, 550)
    (2008, 'Diego', 'Romero', '1188990011', 'diego.romero@example.com', 'password123', 'USUARIO', 540.0, 460.0),
    -- Usuario 9: Cerca de Parada 4 (250, 350)
    (2009, 'Valentina', 'Diaz', '1199001122', 'valentina.diaz@example.com', 'password123', 'USUARIO', 355.0, 245.0),
    -- Usuario 10: En el otro extremo del mapa
    (2010, 'Matias', 'Alvarez', '1100112233', 'matias.alvarez@example.com', 'password123', 'USUARIO', 50.0, 50.0);

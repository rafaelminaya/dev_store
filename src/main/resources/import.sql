-- MARCAS
INSERT INTO marcas (nombre) VALUES ('Ralph Lauren');
INSERT INTO marcas (nombre) VALUES ('Levis');
INSERT INTO marcas (nombre) VALUES ('Calvin Klein');
INSERT INTO marcas (nombre) VALUES ('Lacoste');
INSERT INTO marcas (nombre) VALUES ('Tommy Hilfiger');
INSERT INTO marcas (nombre) VALUES ('Fred Perry');
INSERT INTO marcas (nombre) VALUES ('Percival');
INSERT INTO marcas (nombre) VALUES ('John Smedley');
INSERT INTO marcas (nombre) VALUES ('Cos');
INSERT INTO marcas (nombre) VALUES ('Everlane');
-- PROVEEDORES
INSERT INTO proveedores (ruc, razon_comercial, direccion, telefono) VALUES ('68512874621', 'Lacoste', 'Av Javier Prado 2343', '958746325');
INSERT INTO proveedores (ruc, razon_comercial, direccion, telefono) VALUES ('68512874622', 'Levis', 'Av Los Alisos 5874', '958423058');
INSERT INTO proveedores (ruc, razon_comercial, direccion, telefono) VALUES ('68512874623', 'Calvin Klein', 'Av Los Jasmines 2114', '987023843');
INSERT INTO proveedores (ruc, razon_comercial, direccion, telefono) VALUES ('68512874624', 'Ralph Lauren', 'Calle Los Rosales 152', '968017658');
-- CLIENTES
INSERT INTO clientes (dni, nombre, direccion, telefono) VALUES ('08459685', 'Ismael Rodriguez', 'Lima', '958745863');
INSERT INTO clientes (dni, nombre, direccion, telefono) VALUES ('48569215', 'Sara Flores', 'Surquillo', '968541236');
INSERT INTO clientes (dni, nombre, direccion, telefono) VALUES ('45872014', 'Gabriela Juarez', 'Surco', '968572056');
-- PRODUCTOS
INSERT INTO productos (nombre, precio_compra, precio_venta, marca_id) VALUES ('Camiseta azul hombre', 45.0, 75.0, 1);
INSERT INTO productos (nombre, precio_compra, precio_venta, marca_id) VALUES ('Polo negro hombre', 35.0, 60.5, 1);
INSERT INTO productos (nombre, precio_compra, precio_venta, marca_id) VALUES ('Pantalon Jean azul hombre', 80.0, 120.5, 2);
INSERT INTO productos (nombre, precio_compra, precio_venta, marca_id) VALUES ('Pantalon negro vestir hombre', 100.0, 160.0, 2);
INSERT INTO productos (nombre, precio_compra, precio_venta, marca_id) VALUES ('Camisa vino tinto hombre', 80.0, 140.0, 3);
INSERT INTO productos (nombre, precio_compra, precio_venta, marca_id) VALUES ('Gorra blanca hombre', 100.0, 170.0, 3);
INSERT INTO productos (nombre, precio_compra, precio_venta, marca_id) VALUES ('Correa marron hombre', 110.0, 180.0, 3);
-- GUIA REMISION
INSERT INTO guia_remision (numero, fecha_emision, porcentaje_comision, total, proveedor_id) VALUES ('110', NOW(), 20, 2500, 1);
INSERT INTO guia_remision (numero, fecha_emision, porcentaje_comision, total, proveedor_id) VALUES ('110', NOW(), 25, 4500, 2);
INSERT INTO guia_remision (numero, fecha_emision, porcentaje_comision, total, proveedor_id) VALUES ('112', NOW(), 30, 3000, 3);
-- GUIA REMISION DETALLE
INSERT INTO guia_emision_detalle (cantidad, precio_compra, producto_id, total, guia_remision_id) VALUES (12, 45.0, 1, 540.0, 1);
INSERT INTO guia_emision_detalle (cantidad, precio_compra, producto_id, total, guia_remision_id) VALUES (10, 30.5, 2, 350.0, 1);
INSERT INTO guia_emision_detalle (cantidad, precio_compra, producto_id, total, guia_remision_id) VALUES (18, 80.0, 3, 1440.0, 2);
INSERT INTO guia_emision_detalle (cantidad, precio_compra, producto_id, total, guia_remision_id) VALUES (7, 100.0, 4, 700.0, 2);
INSERT INTO guia_emision_detalle (cantidad, precio_compra, producto_id, total, guia_remision_id) VALUES (20, 80.0, 5, 1600.0, 2);
-- BOLETA VENTA
INSERT INTO boleta_venta (numero, fecha_emision, base_imponible, importe_igv, total, cliente_id) VALUES ('100', NOW(), 111.11, 24.39, 135.5, 1);
-- BOLETA VENTA DETALLE
INSERT INTO boleta_venta_detalle (cantidad, precio_compra, precio_venta, base_imponible, importe_igv, total, producto_id, boleta_venta_id) VALUES (1, 45.0, 75.0, 61.5, 13.5, 75.0, 1, 1);
INSERT INTO boleta_venta_detalle (cantidad, precio_compra, precio_venta, base_imponible, importe_igv, total, producto_id, boleta_venta_id) VALUES (1, 35.0, 60.5, 49.61, 10.89, 60.5, 1, 1);

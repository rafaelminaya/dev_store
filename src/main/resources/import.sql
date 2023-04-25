-- TIPO OPERACION
INSERT INTO tipo_operacion (nombre) VALUES ('Venta');
INSERT INTO tipo_operacion (nombre) VALUES ('Consignacion recibida');
INSERT INTO tipo_operacion (nombre) VALUES ('Consignacion entregada');
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
INSERT INTO productos (codigo, nombre, talla, color, precio_compra, precio_venta, marca_id)  VALUES ('CA-Z', 'Camiseta hombre','M', 'AZUL', 45.0, 75.0, 1);
INSERT INTO productos (codigo, nombre, talla, color, precio_compra, precio_venta, marca_id) VALUES ('CA-Z','Polo hombre','L','NEGRO', 36.3, 60.5, 1);
INSERT INTO productos (codigo, nombre, talla, color, precio_compra, precio_venta, marca_id) VALUES ('CA-P','Pantalon Jean hombre','32','AZUL', 90.0, 120.0, 2);
INSERT INTO productos (codigo, nombre, talla, color, precio_compra, precio_venta, marca_id) VALUES ('CA-P','Pantalon vestir hombre','34','NEGRO', 120.0, 160.0, 2);
INSERT INTO productos (codigo, nombre, talla, color, precio_compra, precio_venta, marca_id) VALUES ('CA-Z','Camisa hombre','S','VINO TINTO', 105.0, 140.0, 3);
INSERT INTO productos (codigo, nombre, talla, color, precio_compra, precio_venta, marca_id) VALUES ('CA-G','Gorra hombre', 'M','BLANCO', 100.0, 170.0, 3);
INSERT INTO productos (codigo, nombre, talla, color, precio_compra, precio_venta, marca_id) VALUES ('CA-C','Correa marron hombre','L','MARRON', 110.0, 180.0, 3);
-- GUIA REMISION
INSERT INTO guia_remision (numero, fecha_emision, porcentaje_comision, proveedor_id, procesado, eliminado) VALUES ('100', DATE_SUB(now(), INTERVAL 2 DAY), 40, 1, 1, 0);
INSERT INTO guia_remision (numero, fecha_emision, porcentaje_comision, proveedor_id, procesado, eliminado) VALUES ('101', DATE_SUB(now(), INTERVAL 2 DAY), 25, 2, 1, 0);
-- GUIA REMISION DETALLE
INSERT INTO guia_emision_detalle (cantidad, precio_venta, producto_id, guia_remision_id, eliminado) VALUES (12, 75.0, 1, 1, 0);
INSERT INTO guia_emision_detalle (cantidad, precio_venta, producto_id, guia_remision_id, eliminado) VALUES (10, 60.5, 2, 1, 0);
INSERT INTO guia_emision_detalle (cantidad, precio_venta, producto_id, guia_remision_id, eliminado) VALUES (18, 120.0, 3, 2, 0);
INSERT INTO guia_emision_detalle (cantidad, precio_venta, producto_id, guia_remision_id, eliminado) VALUES (7, 160.0, 4, 2, 0);
INSERT INTO guia_emision_detalle (cantidad, precio_venta, producto_id, guia_remision_id, eliminado) VALUES (20, 140.0, 5, 2, 0);
-- BOLETA VENTA
INSERT INTO boleta_venta (numero, fecha_emision, cliente_id) VALUES ('500', DATE_SUB(now(), INTERVAL 1 DAY), 1);
-- BOLETA VENTA DETALLE
INSERT INTO boleta_venta_detalle (cantidad, precio_compra, precio_venta, producto_id, boleta_venta_id) VALUES (1, 45.0, 75.0, 1, 1);
INSERT INTO boleta_venta_detalle (cantidad, precio_compra, precio_venta, producto_id, boleta_venta_id) VALUES (1, 35.0, 60.5, 2, 1);
-- KARDEX
INSERT INTO kardex (numero, comprobante_id, fecha_emision, tipo_operacion_id) VALUES ('100', 1, DATE_SUB(now(), INTERVAL 2 DAY), 2);
INSERT INTO kardex (numero, comprobante_id, fecha_emision, tipo_operacion_id) VALUES ('101', 2, DATE_SUB(now(), INTERVAL 2 DAY), 2);
INSERT INTO kardex (numero, comprobante_id, fecha_emision, tipo_operacion_id) VALUES ('500', 1, DATE_SUB(now(), INTERVAL 1 DAY), 1);
-- KARDEX DETALLE
INSERT INTO kardex_detalle (fecha_emision, entrada_cantidad, entrada_precio, entrada_total, salida_cantidad, salida_precio, salida_total, saldo_cantidad, saldo_precio, saldo_total, producto_id, kardex_id) VALUES (DATE_SUB(now(), INTERVAL 2 DAY), 12, 75, 900, 0, 0, 0, 12, 75, 900, 1, 1);
INSERT INTO kardex_detalle (fecha_emision, entrada_cantidad, entrada_precio, entrada_total, salida_cantidad, salida_precio, salida_total, saldo_cantidad, saldo_precio, saldo_total, producto_id, kardex_id) VALUES (DATE_SUB(now(), INTERVAL 2 DAY), 10, 60.5, 605, 0, 0, 0, 10, 60.5, 605, 2, 1);
INSERT INTO kardex_detalle (fecha_emision, entrada_cantidad, entrada_precio, entrada_total, salida_cantidad, salida_precio, salida_total, saldo_cantidad, saldo_precio, saldo_total, producto_id, kardex_id) VALUES (DATE_SUB(now(), INTERVAL 2 DAY), 18, 120.0, 2160.0, 0, 0, 0, 18, 120.0, 2160.0, 3, 2);
INSERT INTO kardex_detalle (fecha_emision, entrada_cantidad, entrada_precio, entrada_total, salida_cantidad, salida_precio, salida_total, saldo_cantidad, saldo_precio, saldo_total, producto_id, kardex_id) VALUES (DATE_SUB(now(), INTERVAL 2 DAY), 7, 160.0, 1120.0, 0, 0, 0, 7, 160.0, 1120.0, 4, 2);
INSERT INTO kardex_detalle (fecha_emision, entrada_cantidad, entrada_precio, entrada_total, salida_cantidad, salida_precio, salida_total, saldo_cantidad, saldo_precio, saldo_total, producto_id, kardex_id) VALUES (DATE_SUB(now(), INTERVAL 2 DAY), 20, 140.0, 2800.0, 0, 0, 0, 20, 140.0, 2800.0, 5, 2);

INSERT INTO kardex_detalle (fecha_emision, entrada_cantidad, entrada_precio, entrada_total, salida_cantidad, salida_precio, salida_total, saldo_cantidad, saldo_precio, saldo_total, producto_id, kardex_id) VALUES (DATE_SUB(now(), INTERVAL 1 DAY), 0, 0, 0, 1, 75.0, 75.0, 11, 75.0, 825, 1, 3);
INSERT INTO kardex_detalle (fecha_emision, entrada_cantidad, entrada_precio, entrada_total, salida_cantidad, salida_precio, salida_total, saldo_cantidad, saldo_precio, saldo_total, producto_id, kardex_id) VALUES (DATE_SUB(now(), INTERVAL 1 DAY), 0, 0, 0, 1, 60.5, 60.5, 9, 60.5, 544.5, 2, 3);


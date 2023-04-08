package com.rminaya.dev.store.model.entity.venta;

import com.rminaya.dev.store.model.entity.common.Producto;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class BoletaVentaTest {
    BoletaVenta boleta1;
    BoletaVenta boleta2;
    Producto productoJean;
    Producto productoPolo;
    BoletaVentaDetalle detalle1;
    BoletaVentaDetalle detalle2;

    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeEach
    void setUp(TestInfo testInfo, TestReporter testReporter) {
        this.boleta1 = new BoletaVenta();
        this.boleta2 = new BoletaVenta();

        this.productoJean = new Producto();
        productoJean.setNombre("Pantalon jean - Talla 30");
        productoJean.setPrecioCompra(90.0);
        productoJean.setPrecioVenta(120.5);

        this.productoPolo = new Producto();
        productoPolo.setNombre("Polo blanco - Talla M");
        productoPolo.setPrecioCompra(20.5);
        productoPolo.setPrecioVenta(35.0);

        this.detalle1 = new BoletaVentaDetalle();
        detalle1.setCantidad(2);
        detalle1.setProducto(this.productoJean);

        this.detalle2 = new BoletaVentaDetalle();
        detalle2.setCantidad(3);
        detalle2.setProducto(this.productoPolo);

        this.testInfo = testInfo;
        this.testReporter = testReporter;

        System.out.println("Iniciando el método de prueba.");

        testReporter.publishEntry(
                "Ejecutando " + testInfo.getDisplayName()
                        + " - " + testInfo.getTestClass().orElseThrow().getName()
                        + " con las etiquetas" + testInfo.getTags());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el método test");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el test.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test.");
    }


    @Test
    @Tag("venta")
    @DisplayName("Probando ventas con 1 detalle")
    void testRegistroVenta() {
        // afirmamos que no hay detalles en la boleta
        assertEquals(0, boleta1.getBoletaVentaDetalles().size(), () -> "La cantidad de detalles es diferente de cero");

        boleta1.setBoletaVentaDetalles(List.of(detalle1));

        // afirmamos que hay 2 productos en total
        assertEquals(2, boleta1.getBoletaVentaDetalles()
                .stream()
                .mapToInt(BoletaVentaDetalle::getCantidad)
                .sum(), () -> "La cantidad de productos no es igual a 5.");


        assertNotEquals(0, boleta1.getBoletaVentaDetalles().size());
        assertTrue(boleta1.getBoletaVentaDetalles().size() > 0);
    }

    @Test
    @Tag("venta")
    @DisplayName("Probando ventas con 2 detalles")
    void testRegistroVentas2() {
        boleta1.setBoletaVentaDetalles(List.of(detalle1, detalle2));

        // afirmamos que hay 4 productos en total}
        assertEquals(5, boleta1.getBoletaVentaDetalles()
                .stream()
                .mapToInt(BoletaVentaDetalle::getCantidad)
                .sum(), () -> "La cantidad de registros no es igual a 5.");
    }

    @Test
    @Tag("venta")
    @DisplayName("Probando 2 ventas  con 5 detalles en total")
    void testRegistroVentas3() {
        boleta1.setBoletaVentaDetalles(List.of(detalle1));
        boleta2.setBoletaVentaDetalles(List.of(detalle1, detalle2));

        assertAll(
                () -> {
                    assertTrue(boleta1.getBoletaVentaDetalles().size() > 0, () -> "La cantidad de detalles no es mayor que cero");
                },
                () -> {
                    assertEquals(5, boleta2.getBoletaVentaDetalles()
                            .stream()
                            .mapToInt(BoletaVentaDetalle::getCantidad)
                            .sum(), () -> "La cantidad de registros no es igual a 5.");
                }
        );
    }

    @Test
    void testRegistroVentas4() {
        this.boleta1.setBoletaVentaDetalles(List.of(detalle1));

        boolean hayDetalles = this.boleta1.getBoletaVentaDetalles().size() > 0;

        assumingThat(hayDetalles, () -> {
            assertNotNull(this.boleta1.getBoletaVentaDetalles(), () -> "Hay detalles en la boleta");
            assertEquals(1, this.boleta1.getBoletaVentaDetalles().size(), () -> "No hay 1 detalle en la boleta");
        });
    }
}
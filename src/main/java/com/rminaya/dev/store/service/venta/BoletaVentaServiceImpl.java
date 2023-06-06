package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.dto.BoletaVentaInDto;
import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import com.rminaya.dev.store.model.entity.almacen.Operacion;
import com.rminaya.dev.store.model.entity.almacen.TipoOperacion;
import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.model.entity.venta.BoletaVentaDetalle;
import com.rminaya.dev.store.model.entity.venta.Cliente;
import com.rminaya.dev.store.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoletaVentaServiceImpl implements BoletaVentaService {

    private final BoletaVentaRepository boletaVentaRepository;
    private final ClienteRespository clienteRespository;
    private final KardexRepository kardexRepository;
    private final KardexDetalleRepository kardexDetalleRepository;
    private final TipoOperacionRepository tipoOperacionRepository;
    private final ProductoRepository productoRepository;

    public BoletaVentaServiceImpl(BoletaVentaRepository boletaVentaRepository,
                                  ClienteRespository clienteRespository,
                                  KardexRepository kardexRepository,
                                  KardexDetalleRepository kardexDetalleRepository,
                                  TipoOperacionRepository tipoOperacionRepository,
                                  ProductoRepository productoRepository) {
        this.boletaVentaRepository = boletaVentaRepository;
        this.clienteRespository = clienteRespository;
        this.kardexRepository = kardexRepository;
        this.kardexDetalleRepository = kardexDetalleRepository;
        this.tipoOperacionRepository = tipoOperacionRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoletaVenta> findAll() {
        return this.boletaVentaRepository.findAll()
                .stream()
                .filter(boletaVenta -> boletaVenta.getEliminado().equals(false))
                .toList();
    }

    @Override
    public Page<BoletaVenta> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());

        return this.boletaVentaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public BoletaVenta findById(Long id) {
        return this.boletaVentaRepository.findById(id)
                .filter(boletaVenta -> boletaVenta.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la venta.", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Long save(BoletaVentaInDto boletaVentaInDto) {

        Optional<Cliente> cliente = this.clienteRespository.findByNumeroDocumento(boletaVentaInDto.getCliente().getNumeroDocumento());

        BoletaVenta boletaVenta = new BoletaVenta();
        boletaVenta.setNumero(boletaVentaInDto.getNumero());
        boletaVenta.setFechaEmision(LocalDateTime.now());

        if (cliente.isEmpty()) {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNumeroDocumento(boletaVentaInDto.getCliente().getNumeroDocumento());
            nuevoCliente.setNombre(boletaVentaInDto.getCliente().getNombre());
            nuevoCliente.setDireccion(boletaVentaInDto.getCliente().getDireccion());
            nuevoCliente = this.clienteRespository.save(nuevoCliente);
            boletaVenta.setCliente(nuevoCliente);
        } else {
            boletaVenta.setCliente(cliente.orElseThrow());
        }

        List<BoletaVentaDetalle> detalles = new ArrayList<>();

        boletaVentaInDto.getBoletaVentaDetalles().forEach(boletaVentaDetalleInDto -> {
            BoletaVentaDetalle boletaVentaDetalle = new BoletaVentaDetalle();
            boletaVentaDetalle.setCantidad(boletaVentaDetalleInDto.getCantidad());

            Producto producto = this.productoRepository.findById(boletaVentaDetalleInDto.getProducto().getId()).orElseThrow();
            boletaVentaDetalle.setProducto(producto);
            boletaVentaDetalle.setPrecioVenta(producto.getPrecioVenta());
            boletaVentaDetalle.setPrecioCompra(producto.getPrecioCompra());

            boletaVentaDetalle.setBaseImponible(boletaVentaDetalle.calcularBaseImponible());
            boletaVentaDetalle.setImporteIgv(boletaVentaDetalle.calcularImporteIgv());
            boletaVentaDetalle.setTotalDetalle(boletaVentaDetalle.calcularTotalDetalle());
            detalles.add(boletaVentaDetalle);
        });

        boletaVenta.setBoletaVentaDetalles(detalles);

        /*
        boletaVenta.getBoletaVentaDetalles().forEach(boletaVentaDetalle -> {
            boletaVentaDetalle.setBaseImponible(boletaVentaDetalle.calcularBaseImponible());
            boletaVentaDetalle.setImporteIgv(boletaVentaDetalle.calcularImporteIgv());
            boletaVentaDetalle.setTotalDetalle(boletaVentaDetalle.calcularTotalDetalle());
        });
        */

        boletaVenta.setBaseImponible(boletaVenta.calcularBaseImponible());
        boletaVenta.setImporteIgv(boletaVenta.calcularImporteIgv());
        boletaVenta.setTotal(boletaVenta.calcularTotal());


        boletaVenta = this.boletaVentaRepository.save(boletaVenta);

        // Almacenar la venta en el kardex y detalles
        Kardex kardex = new Kardex();
        kardex.setNumero(boletaVenta.getNumero());
        kardex.setComprobanteId(boletaVenta.getId());
        kardex.setFechaEmision(LocalDateTime.now());
        // Obtenemos el tipo operación para una "venta"
        TipoOperacion tipoOperacion = tipoOperacionRepository.findById(Operacion.VENTA.getId())
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la la operación.", HttpStatus.NOT_FOUND));
        kardex.setTipoOperacion(tipoOperacion);

        // Creamos el kardex
        this.kardexRepository.save(kardex);

        boletaVenta.getBoletaVentaDetalles().forEach(
                detalle -> {
                    // Obtenemos el ultimo saldo del producto a registrar

                    Integer kardexDetalleUltimoSaldo = this.kardexRepository.KardexDetalleUltimoSaldoCantidad(
                            detalle.getProducto().getId(),
                            LocalDateTime.now());
                    // Obtenemos el ultimo saldo del producto registrado en algun kardex
                    Integer ultimoSaldoCantidad = 0;

                    System.out.println("kardexDetalleUltimoSaldo: " + kardexDetalleUltimoSaldo);

                    if (kardexDetalleUltimoSaldo != null) {
                        ultimoSaldoCantidad = kardexDetalleUltimoSaldo;
                    }

                    System.out.println("ultimoSaldoCantidad: " + ultimoSaldoCantidad);

                    KardexDetalle kardexDetalle = new KardexDetalle();
                    kardexDetalle.setFechaEmision(LocalDateTime.now());
                    kardexDetalle.setProducto(detalle.getProducto());

                    kardexDetalle.setEntradaCantidad(0);
                    kardexDetalle.setEntradaPrecio(0d);
                    kardexDetalle.setEntradaTotal(0d);

                    kardexDetalle.setSalidaCantidad(detalle.getCantidad());
                    kardexDetalle.setSalidaPrecio(detalle.getTotalDetalle() / detalle.getCantidad());
                    kardexDetalle.setSalidaTotal(detalle.getTotalDetalle());

                    kardexDetalle.setSaldoCantidad(ultimoSaldoCantidad - detalle.getCantidad());
                    kardexDetalle.setSaldoPrecio(detalle.getTotalDetalle() / detalle.getCantidad());
                    kardexDetalle.setSaldoTotal((detalle.getTotalDetalle() / detalle.getCantidad()) * (ultimoSaldoCantidad + detalle.getCantidad()));
                    // Añadimos el detalle al kardex
                    kardex.addDetalle(kardexDetalle);


                    // Obtengo los diferentes "kardex detalles" a actualizar según el nuevo moviemiento
                    List<KardexDetalle> kardexDetallesByProducto = this.kardexDetalleRepository.detallesByProductoAndFechaEmision(
                            detalle.getProducto().getId(),
                            LocalDateTime.now());

                    // Re asignamos el último saldo cantidad
                    Integer nuevoUltimoSaldoCantidad = kardexDetalle.getSaldoCantidad();

                    // Iteramos los diferentes "kardex detalles" obtenidos, los cuales serán actualizados con los nuevos saldos
                    for (KardexDetalle detalleByProducto : kardexDetallesByProducto) {

                        KardexDetalle kardexDetalleUpdate = this.kardexDetalleRepository.findById(detalleByProducto.getId())
                                .orElseThrow(() -> new DevStoreExceptions("No hay kardex detalle que actualizar.", HttpStatus.NOT_FOUND));

                        kardexDetalleUpdate.setSaldoCantidad(nuevoUltimoSaldoCantidad + detalleByProducto.getEntradaCantidad() - detalleByProducto.getSalidaCantidad());
                        //TODO faltaria agregar el saldo precio, lo veremos cuando se implemente la aplicación cliente
//                        kardexDetalleUpdate.setSaldoPrecio(0d);
                        kardexDetalleUpdate.setSaldoTotal(detalleByProducto.getSaldoPrecio() * (nuevoUltimoSaldoCantidad + kardexDetalleUpdate.getEntradaCantidad() - detalleByProducto.getSalidaCantidad()));

                        // Actualizamos el "kardex detalle"
                        kardexDetalleUpdate = this.kardexDetalleRepository.save(kardexDetalleUpdate);
                        // Volvemos a re asignar el último saldo cantidad
                        nuevoUltimoSaldoCantidad = kardexDetalleUpdate.getSaldoCantidad();

                        // Actualizamos del stock de cada producto
                        Producto productoBuscado = this.productoRepository.findById(detalle.getProducto().getId())
                                .orElseThrow(() -> new DevStoreExceptions("No se encontró la producto.", HttpStatus.NOT_FOUND));
                        productoBuscado.setStock(nuevoUltimoSaldoCantidad);
                        this.productoRepository.save(productoBuscado);
                    }
                }
        );
        // Creamos el kardex con sus detalles
        this.kardexRepository.save(kardex);

        return boletaVenta.getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.boletaVentaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Kardex saveKardex(Kardex kardex) {
        return this.kardexRepository.save(kardex);
    }

    @Override
    @Transactional
    public void anular(Long boletaVentaId) {
        // Obtenemos la boleta de benta y sus detalles
        BoletaVenta boletaVenta = this.boletaVentaRepository.findById(boletaVentaId)
                .filter(boletaVenta1 -> boletaVenta1.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la boleta de venta.", HttpStatus.NOT_FOUND));
        boletaVenta.setEliminado(true);
        // Iteramos los detalles de la boleta, para buscar los detalles de los kardex y actualizar sus saldos
        boletaVenta.getBoletaVentaDetalles().forEach(
                detalle -> {
                    List<KardexDetalle> kardexDetallesByProducto = this.kardexDetalleRepository.detallesByProductoAndFechaEmision(
                            detalle.getProducto().getId(),
                            boletaVenta.getFechaEmision());
                    // Obtenemos el ultimo saldo del producto a registrar
                    Integer kardexDetalleUltimoSaldo = this.kardexRepository.KardexDetalleUltimoSaldoCantidad(
                            detalle.getProducto().getId(),
                            boletaVenta.getFechaEmision());
                    // Obtenemos el ultimo saldo del producto registrado en algun kardex
                    Integer ultimoSaldoCantidad = 0;

                    System.out.println("kardexDetalleUltimoSaldo: " + kardexDetalleUltimoSaldo);

                    if (kardexDetalleUltimoSaldo != null) {
                        ultimoSaldoCantidad = kardexDetalleUltimoSaldo;
                    }

                    System.out.println("ultimoSaldoCantidad: " + ultimoSaldoCantidad);

                    // Iteramos los diferentes "kardex detalles" obtenidos, los cuales serán actualizados con los nuevos saldos
                    for (KardexDetalle detalleByProducto : kardexDetallesByProducto) {

                        KardexDetalle kardexDetalleUpdate = this.kardexDetalleRepository.findById(detalleByProducto.getId())
                                .orElseThrow(() -> new DevStoreExceptions("No hay kardex detalle que actualizar.", HttpStatus.NOT_FOUND));

                        kardexDetalleUpdate.setSaldoCantidad(ultimoSaldoCantidad + detalleByProducto.getEntradaCantidad() - detalleByProducto.getSalidaCantidad());
                        //TODO faltaria agregar el saldo precio, lo veremos cuando se implemente la aplicación cliente
//                        kardexDetalleUpdate.setSaldoPrecio(0d);
                        kardexDetalleUpdate.setSaldoTotal(detalleByProducto.getSaldoPrecio() * (ultimoSaldoCantidad + detalleByProducto.getEntradaCantidad() - detalleByProducto.getSalidaCantidad()));
                        // Actualizamos el "kardex detalle"
                        kardexDetalleUpdate = this.kardexDetalleRepository.save(kardexDetalleUpdate);
                        // Re asignamos el último saldo cantidad
                        ultimoSaldoCantidad = kardexDetalleUpdate.getSaldoCantidad();

                        // Actualizamos del stock de cada producto
                        Producto productoBuscado = this.productoRepository.findById(detalle.getProducto().getId())
                                .orElseThrow(() -> new DevStoreExceptions("No se encontró la producto.", HttpStatus.NOT_FOUND));
                        productoBuscado.setStock(ultimoSaldoCantidad);
                        this.productoRepository.save(productoBuscado);
                    }
                }
        );

        TipoOperacion tipoOperacion = this.tipoOperacionRepository.findById(Operacion.VENTA.getId())
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la la operación.", HttpStatus.NOT_FOUND));
        // Buscamos el kardex y sus detalles para eliminarlos
        Kardex kardex = this.kardexRepository.kardexByIdAndTipoOperacion(boletaVentaId, tipoOperacion.getId())
                .filter(kardex1 -> kardex1.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró el kardex", HttpStatus.NOT_FOUND));
        // Elimnamos el kardex y sus detalles
        kardex.setEliminado(true);
        kardex.getKardexDetalles().forEach(kardexDetalle -> {
            kardexDetalle.setEliminado(true);
        });
        // Guardamos los cambios del cambio de estado a eliminado
        this.kardexRepository.save(kardex);
        // Elimnamos la venta y sus detalles
        boletaVenta.getBoletaVentaDetalles().forEach(boletaVentaDetalle -> {
            boletaVentaDetalle.setEliminado(true);
        });
        this.boletaVentaRepository.save(boletaVenta);

    }
}

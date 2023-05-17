package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import com.rminaya.dev.store.model.entity.almacen.Operacion;
import com.rminaya.dev.store.model.entity.almacen.TipoOperacion;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.repository.BoletaVentaRepository;
import com.rminaya.dev.store.repository.KardexDetalleRepository;
import com.rminaya.dev.store.repository.KardexRepository;
import com.rminaya.dev.store.repository.TipoOperacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoletaVentaServiceImpl implements BoletaVentaService {

    private final BoletaVentaRepository boletaVentaRepository;
    private final KardexRepository kardexRepository;
    private final KardexDetalleRepository kardexDetalleRepository;
    private final TipoOperacionRepository tipoOperacionRepository;

    public BoletaVentaServiceImpl(BoletaVentaRepository boletaVentaRepository,
                                  KardexRepository kardexRepository,
                                  KardexDetalleRepository kardexDetalleRepository,
                                  TipoOperacionRepository tipoOperacionRepository) {
        this.boletaVentaRepository = boletaVentaRepository;
        this.kardexRepository = kardexRepository;
        this.kardexDetalleRepository = kardexDetalleRepository;
        this.tipoOperacionRepository = tipoOperacionRepository;
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

        return this.boletaVentaRepository.findAllByEliminado(false, pageable);
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
    public Long save(BoletaVenta boletaVenta) {
        // Crear boleta de venta y detalles
        boletaVenta.setFechaEmision(LocalDateTime.now());

        boletaVenta.getBoletaVentaDetalles().forEach(boletaVentaDetalle -> {
            boletaVentaDetalle.setBaseImponible(boletaVentaDetalle.calcularBaseImponible());
            boletaVentaDetalle.setImporteIgv(boletaVentaDetalle.calcularImporteIgv());
            boletaVentaDetalle.setTotalDetalle(boletaVentaDetalle.calcularTotalDetalle());
        });

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

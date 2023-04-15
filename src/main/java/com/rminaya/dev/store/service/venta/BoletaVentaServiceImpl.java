package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import com.rminaya.dev.store.model.entity.almacen.Operacion;
import com.rminaya.dev.store.model.entity.almacen.TipoOperacion;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.repository.BoletaVentaRepository;
import com.rminaya.dev.store.repository.KardexRepository;
import com.rminaya.dev.store.repository.TipoOperacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoletaVentaServiceImpl implements BoletaVentaService {

    private final BoletaVentaRepository boletaVentaRepository;
    private final KardexRepository kardexRepository;
    private final TipoOperacionRepository tipoOperacionRepository;

    public BoletaVentaServiceImpl(BoletaVentaRepository boletaVentaRepository, KardexRepository kardexRepository, TipoOperacionRepository tipoOperacionRepository) {
        this.boletaVentaRepository = boletaVentaRepository;
        this.kardexRepository = kardexRepository;
        this.tipoOperacionRepository = tipoOperacionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoletaVenta> findAll() {
        return this.boletaVentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BoletaVenta findById(Long id) {
        return this.boletaVentaRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public BoletaVenta save(BoletaVenta boletaVenta) {

        // Crear boleta de venta y detalles
        boletaVenta.setFechaEmision(LocalDateTime.now());
        boletaVenta = this.boletaVentaRepository.save(boletaVenta);
        // Almacenar la venta en el kardex y detalles
        Kardex kardex = new Kardex();
        kardex.setNumero(boletaVenta.getNumero());
        kardex.setComprobanteId(boletaVenta.getId());
        kardex.setFechaEmision(LocalDateTime.now());
        //kardex.setTipoOperacion(1);
        TipoOperacion tipoOperacion = tipoOperacionRepository.findById(Operacion.VENTA.getId()).orElseThrow();
        kardex.setTipoOperacion(tipoOperacion);

        //TODO : Esto falta añaadir los 3 últimos campos del KardexDetalle correspondientes a los saldos
        boletaVenta.getBoletaVentaDetalles().forEach(
                detalle -> {
                    KardexDetalle kardexDetalle = new KardexDetalle();
                    kardexDetalle.setFechaEmision(LocalDateTime.now());
                    kardexDetalle.setProducto(detalle.getProducto());
                    kardexDetalle.setEntradaCantidad(0);
                    kardexDetalle.setEntradaPrecio(0d);
                    kardexDetalle.setEntradaTotal(0d);
                    kardexDetalle.setSalidaCantidad(detalle.getCantidad());
                    kardexDetalle.setSalidaPrecio(detalle.getPrecioVenta());
                    kardexDetalle.setSalidaTotal(detalle.getTotal());
                    kardex.addDetalle(kardexDetalle);
                }
        );
        this.kardexRepository.save(kardex);

        return boletaVenta;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.boletaVentaRepository.deleteById(id);
    }

    @Override
    public Kardex saveKardex(Kardex kardex) {
        return this.kardexRepository.save(kardex);
    }
}

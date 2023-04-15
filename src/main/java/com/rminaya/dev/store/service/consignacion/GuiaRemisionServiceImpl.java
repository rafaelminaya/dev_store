package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import com.rminaya.dev.store.model.entity.almacen.Operacion;
import com.rminaya.dev.store.model.entity.almacen.TipoOperacion;
import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.repository.GuiaRemisionRepository;
import com.rminaya.dev.store.repository.KardexDetalleRepository;
import com.rminaya.dev.store.repository.KardexRepository;
import com.rminaya.dev.store.repository.TipoOperacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuiaRemisionServiceImpl implements GuiaRemisionService {
    private final GuiaRemisionRepository guiaRemisionRepository;
    private final KardexRepository kardexRepository;
    private final KardexDetalleRepository kardexDetalleRepository;
    private final TipoOperacionRepository tipoOperacionRepository;


    public GuiaRemisionServiceImpl(GuiaRemisionRepository guiaRemisionRepository,
                                   KardexRepository kardexRepository,
                                   KardexDetalleRepository kardexDetalleRepository,
                                   TipoOperacionRepository tipoOperacionRepository) {
        this.guiaRemisionRepository = guiaRemisionRepository;
        this.kardexRepository = kardexRepository;
        this.kardexDetalleRepository = kardexDetalleRepository;
        this.tipoOperacionRepository = tipoOperacionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuiaRemision> findAll() {
        return this.guiaRemisionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GuiaRemision findById(Long id) {
        return this.guiaRemisionRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public GuiaRemision save(GuiaRemision guiaRemision) {
        System.out.println("SERVICE: " + guiaRemision);

        guiaRemision.getGuiaRemisionDetalles()
                .forEach(guiaRemisionDetalle -> guiaRemisionDetalle.setGuiaRemision(guiaRemision));

        return this.guiaRemisionRepository.save(guiaRemision);
    }

    @Override
    @Transactional
    public void procesarKardex(Long guiaRemisionId) {
        // Buscamos la guia con sus detalles, que exista en la BBDD y que no esté procesada.
        GuiaRemision guiaRemisionBuscada = this.guiaRemisionRepository.findByIdAndNoProcesado(guiaRemisionId)
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la guía o ya ha sido procesada.", HttpStatus.NOT_FOUND));
        guiaRemisionBuscada.setProcesado(true);
        this.guiaRemisionRepository.save(guiaRemisionBuscada);

        // Registramos el kardex y sus detalles
        Kardex kardex = new Kardex();
        kardex.setNumero(guiaRemisionBuscada.getNumero());
        kardex.setComprobanteId(guiaRemisionBuscada.getId());
        kardex.setFechaEmision(guiaRemisionBuscada.getFechaEmision());
        //kardex.setTipoOperacion(2);
        // Obtenemos el tipo operación para una consignación recibida
        TipoOperacion tipoOperacion = tipoOperacionRepository.findById(Operacion.CONSIGNACION_RECIBIDA.getId())
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la la operación.", HttpStatus.NOT_FOUND));
        kardex.setTipoOperacion(tipoOperacion);

        // Creamos el kardex
        this.kardexRepository.save(kardex);

        // Iteramos los detalles de la guía para registrar su "kardex detalle" y actualizar los saldos correspondientes.
        guiaRemisionBuscada.getGuiaRemisionDetalles()
                .forEach(detalle -> {
                            // TODO : Esto aparentemente ya está terminado, verificar y replicar en el "save()" de "BoletaVentaServiceImpl" y en "desprocesarKardex" de la clase actual.

                            // Obtenemos el ultimo saldo del producto a registrar
                            Optional<KardexDetalle> kardexDetalleUltimoSaldo = this.kardexRepository.KardexDetalleUltimoSaldoCantidad(
                                    detalle.getProducto().getId(),
                                    detalle.getGuiaRemision().getFechaEmision());
                            // Obtenemos el ultimo saldo del producto registrado en algun kardex
                            Integer ultimoSaldoCantidad = 0;
                            if (kardexDetalleUltimoSaldo.isPresent()) {
                                ultimoSaldoCantidad = kardexDetalleUltimoSaldo.get().getSaldoCantidad();
                            }

                            // Registramos los detalles del kardex
                            KardexDetalle kardexDetalle = new KardexDetalle();
                            kardexDetalle.setFechaEmision(guiaRemisionBuscada.getFechaEmision());
                            kardexDetalle.setProducto(detalle.getProducto());

                            kardexDetalle.setEntradaCantidad(detalle.getCantidad());
                            kardexDetalle.setEntradaPrecio(detalle.getTotal() / detalle.getCantidad());
                            kardexDetalle.setEntradaTotal(detalle.getTotal());

                            kardexDetalle.setSalidaCantidad(0);
                            kardexDetalle.setSalidaPrecio(0d);
                            kardexDetalle.setSalidaTotal(0d);

                            kardexDetalle.setSaldoCantidad(ultimoSaldoCantidad + detalle.getCantidad());
                            kardexDetalle.setSaldoPrecio(detalle.getTotal() / detalle.getCantidad());
                            kardexDetalle.setSaldoTotal((detalle.getTotal() / detalle.getCantidad()) * (ultimoSaldoCantidad + detalle.getCantidad()));
                            // Añadimos el detalle al kardex
                            kardex.addDetalle(kardexDetalle);

                            // Obtengo los diferentes "kardex detalles" a actualizar según el nuevo moviemiento
                            List<KardexDetalle> kardexDetallesByProducto = this.kardexDetalleRepository.detallesByProductoAndFechaEmision(
                                    detalle.getProducto().getId(),
                                    guiaRemisionBuscada.getFechaEmision());

                            // Re asignamos el último saldo cantidad
                            Integer nuevoUltimoSaldoCantidad = kardexDetalle.getSaldoCantidad();

                            // Iteramos los diferentes "kardex detalles" obtenidos, los cuales serán actualizados con los nuevos saldos
                            for (KardexDetalle detalleByProducto : kardexDetallesByProducto) {

                                KardexDetalle kardexDetalleUpdate = this.kardexDetalleRepository.findById(detalleByProducto.getId())
                                        .orElseThrow(() -> new DevStoreExceptions("No hay kardex detalle que actualizar.", HttpStatus.NOT_FOUND));

                                kardexDetalleUpdate.setSaldoCantidad(nuevoUltimoSaldoCantidad + detalleByProducto.getEntradaCantidad() - detalleByProducto.getSalidaCantidad());
                                kardexDetalleUpdate.setSaldoTotal(detalleByProducto.getSaldoPrecio() * kardexDetalleUpdate.getSaldoCantidad());

                                // Actualizamos el "kardex detalle"
                                kardexDetalleUpdate = this.kardexDetalleRepository.save(kardexDetalleUpdate);
                                // Volvemos a re asignar el último saldo cantidad
                                nuevoUltimoSaldoCantidad = kardexDetalleUpdate.getSaldoCantidad();
                            }
                        }
                );

        // Creamos el kardex con sus detalles
        this.kardexRepository.save(kardex);
    }

    @Override
    public Kardex desprocesarKardex(Long guiaRemisionId) {
        // obtenemos la guia de remision
        GuiaRemision remisionBuscada = this.guiaRemisionRepository.findById(guiaRemisionId).orElseThrow();
        // actualizamos el estado de procesado a falso
        remisionBuscada.setProcesado(false);
        this.guiaRemisionRepository.save(remisionBuscada);


        remisionBuscada.getGuiaRemisionDetalles().forEach(guiaRemisionDetalle -> {
            //Obtengo los arreglos de los detalles del kardex a actualizar
            List<KardexDetalle> kardexDetalles = this.kardexRepository.KardexDetalleByProductoByFechaEmision(guiaRemisionDetalle.getProducto().getId(), guiaRemisionDetalle.getGuiaRemision().getFechaEmision());

            KardexDetalle kardexDetalle = this.kardexRepository.KardexDetalleUltimoSaldoCantidad(guiaRemisionDetalle.getProducto().getId(), guiaRemisionDetalle.getGuiaRemision().getFechaEmision()).orElse(null);
            Integer saldoCantidad = 0;
            if (kardexDetalle != null) {
                saldoCantidad = kardexDetalle.getSaldoCantidad();
            }

            kardexDetalles.forEach(detalle -> {
                KardexDetalle kardexDetalleActualizar = this.kardexDetalleRepository.findById(detalle.getId()).orElseThrow();


            });


        });

        // buscar kardex que coincidan con la guia id y lo eliminamos
        Kardex kardexBuscado = this.kardexRepository.kardexByConsignacion(guiaRemisionId, 2L).orElseThrow();
        this.kardexRepository.deleteById(kardexBuscado.getId());


        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.guiaRemisionRepository.deleteById(id);
    }
}

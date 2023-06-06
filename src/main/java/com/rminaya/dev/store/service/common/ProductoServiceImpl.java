package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.dto.ProductoByMarca;
import com.rminaya.dev.store.model.entity.common.Marca;
import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.repository.KardexRepository;
import com.rminaya.dev.store.repository.MarcaRepository;
import com.rminaya.dev.store.repository.ProductoRepository;
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
public class ProductoServiceImpl implements ProductoService {
    // ATRIBUTOS - Dependencias
    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;
    private final KardexRepository kardexRepository;

    // CONSTRUCTOR - Inyección de dependencias
    public ProductoServiceImpl(ProductoRepository productoRepository, MarcaRepository marcaRepository, KardexRepository kardexRepository) {
        this.productoRepository = productoRepository;
        this.marcaRepository = marcaRepository;
        this.kardexRepository = kardexRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return this.productoRepository.findAll()
                .stream()
                .filter(producto -> producto.getEliminado().equals(false))
                .toList();
    }

    @Override
    public Page<Producto> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());

        return this.productoRepository.findAllByEliminado(false, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return this.productoRepository.findById(id)
                .filter(producto -> producto.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró el producto.", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Producto> findByFiltroVenta(String termino) {
//        List<Producto> productosEncontrados = this.productoRepository.findByFiltroVenta(termino);
//        productosEncontrados.forEach(producto -> {
//
//            Integer kardexDetalleUltimoSaldo = this.kardexRepository.KardexDetalleUltimoSaldoCantidad(
//                    producto.getId(),
//                    LocalDateTime.now());
//            Integer ultimoSaldoCantidad = 0;
//
//            if (kardexDetalleUltimoSaldo != null) {
//                ultimoSaldoCantidad = kardexDetalleUltimoSaldo;
//            }
//        });


        return this.productoRepository.findByFiltroVenta(termino);
    }

    @Override
    @Transactional
    public Long save(Producto producto) {
        if (producto.getId() != null && producto.getId() > 0) {
            producto.setId(0L);
        }
        return this.productoRepository.save(producto).getId();
    }

    @Override
    public Long update(Producto producto, Long id) {
        Producto productoBuscado = this.findById(id);
        productoBuscado.setCodigo(producto.getCodigo());
        productoBuscado.setNombre(producto.getNombre());
        productoBuscado.setTalla(producto.getTalla());
        productoBuscado.setColor(producto.getColor());
        productoBuscado.setPrecioCompra(producto.getPrecioCompra());
        productoBuscado.setPrecioVenta(producto.getPrecioVenta());
        productoBuscado.setMarca(producto.getMarca());
        return this.productoRepository.save(productoBuscado).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Producto producto = this.findById(id);
        producto.setEliminado(true);
        this.productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByMarcaId(Long marcaId) {
        Marca marcaBuscada = this.marcaRepository.findById(marcaId)
                .filter(marca -> marca.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la marca.", HttpStatus.NOT_FOUND));

        return  this.productoRepository.findByMarcaId(marcaBuscada.getId());
    }
}

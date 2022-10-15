package com.bintech.backend_ecommerce.services;

import com.bintech.backend_ecommerce.entity.Producto;
import com.bintech.backend_ecommerce.repository.ProductoRepository;
import com.bintech.backend_ecommerce.services.interfaces.IProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase tipo Servicio para el manejo de Producto.
 *
 * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
 * @version 1.0.0 2022-10-14
 * @since 1.0.0
 */
@Service
public class ProductoService implements IProducto {
    /**
     * Repositorio de producto
     */
    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Devuelve los productos del sistema
     *
     * @return
     */

    @Override
    public List<Producto> productos() {
        return productoRepository.findAll();
    }

    /**
     * Crea un nuevo Producto
     *
     * @param producto Recibe los datos del producto
     * @return Devuelve el objeto Producto creado o null si ya existe
     */
    @Override
    @Transactional
    public Producto createProducto(Producto producto) {
        var productoExiste = productoRepository.findById(producto.getId());
        if (productoExiste.isPresent()) {
            return null;
        }else{
            return productoRepository.save(producto);
        }

    }

    /**
     * Devuelve un producto
     *
     * @param id Recibe id del producto
     * @return
     */
    @Override
    public Producto getOneProducto(Long id) {
        var producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            return producto.get();
        }
        return null;
    }

    /**
     * Actualiza los datos de un producto
     *
     * @param id       Recibe id del producto
     * @param producto Recibe los datos del producto
     * @return
     */
    @Override
    @Transactional
    public Producto updateProducto(Long id, Producto producto) {
        var productoUpdate = productoRepository.findById(id);
        if (productoUpdate.isPresent()) {
            productoUpdate.get().setNombre(producto.getNombre());
            productoUpdate.get().setCaracteristica(producto.getCaracteristica());
            productoUpdate.get().setPrecio(producto.getPrecio());
            productoUpdate.get().setStock(producto.getStock());
            return productoRepository.save(productoUpdate.get());
        }
        return null;
    }


}

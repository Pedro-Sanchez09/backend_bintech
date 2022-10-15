package com.bintech.backend_ecommerce.services.interfaces;

import com.bintech.backend_ecommerce.entity.Producto;

import java.util.List;

/**
 * Interface para el servicio de Producto.
 *
 * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
 * @version 1.0.0 2022-10-14
 * @since 1.0.0
 */

public interface IProducto {
    /**
     * Obtiene todos los productos de la bd
     * @return Devuelve una lista tipo Producto
     */
    List<Producto> productos();

    /**
     * Registra un objeto Producto en la base de datos
     * @param producto Recibe los datos del producto
     * @return Devuelve el objeto Producto creado
     */
    Producto createProducto(Producto producto);

    /**
     * Obtiene un producto específico
     * @param id Recibe id del producto
     * @return Devuelve el producto buscado o null
     */
    Producto getOneProducto(Long id);

    /**
     * Actualiza un producto
     * @param id Recibe id del producto
     * @param producto Recibe los datos del producto
     * @return Devuelve el objeto Producto actualizado
     */
    Producto updateProducto(Long id, Producto producto);


}

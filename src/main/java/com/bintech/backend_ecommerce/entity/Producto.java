package com.bintech.backend_ecommerce.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Entidad Producto
 *
 * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
 * @version 1.0.0 2022-10-14
 * @since 1.0.0
 */
@Entity
@Data
@Table(name = "producto")
public class Producto {
    /**
     * Identificador de la tupla.
     */
    @Id
    @Column(name = "idProducto", nullable = false)
    private Long id;
    /**
     * Recibe el nombre
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    /**
     * Recibe el precio
     */
    @Column(name = "precio", nullable = false, precision = 2)
    private BigDecimal precio;
    /**
     * Recibe las características
     */
    @Column(name = "caracteristica", length = 300)
    private String caracteristica;
    /**
     * Recibe la ruta de la imagen
     */
    @Column(name = "imagen", nullable = false, length = 200)
    private String imagen;

}
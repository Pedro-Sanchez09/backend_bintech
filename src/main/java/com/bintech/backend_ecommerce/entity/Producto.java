package com.bintech.backend_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "producto")
public class Producto  implements Serializable {
    /**
     * Variable usada para manejar el tema del identificador de la tupla (consecutivo)
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idProducto", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 11, scale = 2)
    private BigDecimal precio;

    @Column(name = "caracteristica", length = 300)
    private String caracteristica;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    /**
     * Punto de enlace con la entidad Jugador(un jugador puede tener muchos cartones).
     */
    @OneToMany(
            fetch = FetchType.EAGER,
            targetEntity = Imagen.class,
            cascade = CascadeType.REMOVE,
            mappedBy = "idProducto"
    )
    @JsonManagedReference(value = "producto_imagen")
    private List<Imagen> imagenes = new ArrayList<>();


}
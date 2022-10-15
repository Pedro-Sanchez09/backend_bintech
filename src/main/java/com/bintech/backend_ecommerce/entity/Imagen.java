package com.bintech.backend_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "imagen")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Imagen  implements Serializable{

    /**
     * Variable usada para manejar el tema del identificador de la tupla (consecutivo)
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 45)
    private String tipo;

    @Column(name = "imagenData", nullable = false)
    private byte[] imagenData;


    /**
     * Punto de enlace con la entidad Jugador (un jugador puede tener muchos cartones).
     */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Producto.class, optional = false)
    @JoinColumn(name = "idProducto", nullable = false)
    @JsonBackReference(value = "producto_imagen")
    private Producto idProducto;


}
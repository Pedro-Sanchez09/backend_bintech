package com.bintech.backend_ecommerce.repository;

import com.bintech.backend_ecommerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Producto
 *
 * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
 * @version 1.0.0 2022-10-14
 * @since 1.0.0
 */
public interface ProductoRepository extends JpaRepository<Producto,Long> {
}

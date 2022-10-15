package com.bintech.backend_ecommerce.repository;

import com.bintech.backend_ecommerce.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagenRepository extends JpaRepository<Imagen,Long> {

   Optional<Imagen> findByNombre(String nombreImagen);
}

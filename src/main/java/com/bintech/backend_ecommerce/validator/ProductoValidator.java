package com.bintech.backend_ecommerce.validator;

import com.bintech.backend_ecommerce.entity.Producto;
import com.bintech.backend_ecommerce.utility.validations.ApiUnProcessableEntity;
import org.springframework.stereotype.Service;

/**
 * Interface para la validación de datos recibidos al crear o editar un producto
 * @author  Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
 */
@Service
public interface ProductoValidator {

 void Validator(Producto producto) throws ApiUnProcessableEntity;
 void validatorImage(String imagen) throws ApiUnProcessableEntity;
}

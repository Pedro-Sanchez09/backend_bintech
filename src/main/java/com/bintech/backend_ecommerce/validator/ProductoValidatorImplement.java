package com.bintech.backend_ecommerce.validator;

import com.bintech.backend_ecommerce.entity.Producto;
import com.bintech.backend_ecommerce.utility.validations.ApiUnProcessableEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * Clase para implementar ProductoValidator
 *
 * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
 * @version 1.0.0 2022-10-14
 * @since 1.0.0
 */
@Component
public class ProductoValidatorImplement implements ProductoValidator {

    /**
     * Realiza validaciones de los datos recibidos
     *
     * @param producto Recibe daos tipos Producto
     * @throws ApiUnProcessableEntity Lanza excepción si no cumple las validaciones
     */
    @Override
    public void Validator(Producto producto) throws ApiUnProcessableEntity {
        if ((producto.getPrecio().compareTo(BigDecimal.valueOf(0)) <= 0)) {
            message("El precio debe ser mayor que 0");
        }
    }

    /**
     * Valida el tipo de imagen
     * @param imagen Recibe la ruta de la imagen
     * @throws ApiUnProcessableEntity Lanza la excepción con el mensaje personalizado
     */
    @Override
    public void validatorImage(String imagen) throws ApiUnProcessableEntity {
        if(!(imagen.contains(".png"))&& (!imagen.contains(".jpg"))){
            message("La imagen debe ser .jpg o .png");
        }
    }

    /**
     * Asigna mensaje de excepción
     *
     * @param message Recibe el mensaje asignado
     * @throws ApiUnProcessableEntity Lanza la excepción con el mensaje personalizado
     */
    private void message(String message) throws ApiUnProcessableEntity {
        throw new ApiUnProcessableEntity(message);
    }
}

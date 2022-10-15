package com.bintech.backend_ecommerce.controllers;

import com.bintech.backend_ecommerce.entity.Imagen;
import com.bintech.backend_ecommerce.entity.Producto;
import com.bintech.backend_ecommerce.services.ImagenService;
import com.bintech.backend_ecommerce.services.ProductoService;
import com.bintech.backend_ecommerce.utility.answers.Response;
import com.bintech.backend_ecommerce.validator.ProductoValidatorImplement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class ProductoController {

    /**
     * Servicio para el manejo de Producto
     */
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ImagenService imagenService;

    /**
     * Servicio para validar los datos recibidos
     */
    @Autowired
    private ProductoValidatorImplement productoValidatorImplement;

    /**
     * Variable para el manejo de las respuestas de la API
     */
    private final Response response = new Response();
    /**
     * Variable para el manejo de los códigos de respuesta de la API
     */
    private HttpStatus httpStatus = HttpStatus.OK;

    /**
     * Administrador para las excepciones del sistema
     *
     * @param exception Objeto Exception
     * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
     * @since 1.0.0
     */
    private void getErrorMessageInternal(Exception exception) {
        response.error = true;
        response.message = exception.getMessage();
        response.data = exception.getCause();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private void getErroMessageFormData(IOException exception) {
        response.error = true;
        response.message = exception.getMessage();
        response.data = exception.getCause();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Administrador para las excepciones a nivel de SQL con respecto al manejo del acceso a los datos.
     *
     * @param exception Objeto DataAccessException.
     * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
     * @since 1.0.0
     */
    private void getErrorMessageForResponse(DataAccessException exception) {
        response.error = true;
        if (exception.getRootCause() instanceof SQLException) {
            SQLException sqlEx = (SQLException) exception.getRootCause();
            var sqlErrorCode = sqlEx.getErrorCode();
            switch (sqlErrorCode) {
                case 1062:
                    response.message = "El dato ya está registrado";
                    break;
                case 1452:
                    response.message = "El usuario indicado no existe";
                    break;
                default:
                    response.message = exception.getMessage();
                    response.data = exception.getCause();
                    break;
            }
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            response.message = exception.getMessage();
            response.data = exception.getCause();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * Entrada para consultar los productos
     *
     * @return Devuelve una lista de tipo Producto
     */

    @GetMapping("/productos")
    public ResponseEntity<Response> getProductos() {
        response.restart();
        try {
            response.data = productoService.productos();
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Obtiene un Producto
     *
     * @param idProducto Recibe id del producto
     * @return Devuelve un objeto Producto
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<Response> getOneProducto(@PathVariable("id") Long idProducto) {
        response.restart();
        try {
            log.info("Get producto con id: " + idProducto);
            response.data = productoService.getOneProducto(idProducto);
            httpStatus = HttpStatus.OK;
            if (response.data == null) {
                response.message = "Producto con id: " + idProducto + ", no existe en el sistema";
            }
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }


    @GetMapping("/productos/imagen/{nombreImagen}")
    public ResponseEntity<?> getImagen(@PathVariable("nombreImagen") String nombreImagen) {
        response.restart();
        log.info("Get producto imagen con nombre: " + nombreImagen);
        byte[] img = imagenService.descargarImagen(nombreImagen);
        httpStatus = HttpStatus.OK;
        String tipoIm = "image/jpeg";
        if (img == null) {
            httpStatus = HttpStatus.NOT_FOUND;
            tipoIm = "application/json";
          response.data="Imagen no existe";
        }else{
            if (nombreImagen.contains(".png")) {
                tipoIm = "image/png";
            }
            response.data=img;
        }


        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(tipoIm))
                .body(response.data);
    }

    /**
     * Entrada para crear un Producto en el sistema
     *
     * @return Devuelve el objeto Producto creado
     */
    @PostMapping("/productos")
    public ResponseEntity<Response> createProducto(@RequestBody Producto producto) {
        response.restart();
        try {

            log.info("Producto a crear: " + producto);
            response.data = productoService.createProducto(producto);
            if (response.data == null) {
                response.error = true;
                response.message = "Error, id duplicado";
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            } else {
                httpStatus = HttpStatus.CREATED;
            }

        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    @PostMapping("/productos/{id}/imagen")
    public ResponseEntity<Imagen> subirImagen(@PathVariable("id") Long idProducto, @RequestParam("file") MultipartFile imagen) {
        response.restart();
        try {
            Producto producto = new Producto();
            producto.setId(idProducto);

            log.info("Agregar imagen al producto co id:" + idProducto);
            response.data = imagenService.subirImagen(imagen, producto);
            if (response.data == null) {
                response.error = true;
                response.message = "Error,  no se pudo agregar la  imagen";
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            } else {
                httpStatus = HttpStatus.CREATED;
            }

        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }


    /**
     * Entrada para actualizar los datos de un producto
     *
     * @param idProducto Recibe id del producto
     * @param producto   Recibe objeto Producto
     * @return Devuelve objeto Producto actualizado
     */
    @PutMapping("/productos/{id}")
    public ResponseEntity<Response> updateProducto(@PathVariable("id") Long idProducto, @RequestBody Producto
            producto) {
        response.restart();
        try {
            log.info("Actualizar producto con id: " + idProducto);
            this.productoValidatorImplement.Validator(producto);
            response.data = productoService.updateProducto(idProducto, producto);
            httpStatus = HttpStatus.OK;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }

    /**
     * Actualiza la ruta de la imagen de un Producto
     *
     * @param idProducto Recibe id del producto
     * @param producto   Recibe objeto Producto
     * @return Devuelve true o false
     */
    /*
    @PutMapping("/productos/{id}/imagen")
    public ResponseEntity<Response> updateImagenByProducto(@PathVariable("id") Long
                                                                   idProducto, @RequestBody Producto producto) {
        response.restart();
        try {
            log.info("Actualizar imagen del producto con id: " + idProducto);


            httpStatus = HttpStatus.OK;

        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity(response, httpStatus);
    }*/
}

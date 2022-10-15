package com.bintech.backend_ecommerce.utility;

/**
 * Clase para el manejo de las respuestas de la API
 *
 * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
 * @version 1.0.0 2022-10-14
 * @since 1.0.0
 */
public class Response {

    /**
     * Indica de si existe un error o no en la respuesta de la API.
     */
    public Boolean error;

    /**
     * Mensaje de la API cuando es utilizada.
     */
    public String message;

    /**
     * Información del API cuando es necesario.
     */
    public Object data;

    /**
     * Constructor de la clase.
     *
     * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
     * @since 1.0.0
     */
    public Response() {
        error = false;
        message = "";
        data = null;
    }

    /**
     * Restaura los valores de la respuesta del API
     *
     * @author Pedro Luis Sanchez Calle <psanchezc09@gmail.com>
     * @since 1.0.0
     */
    public void restart() {
        error = false;
        message = "";
        data = null;
    }
}

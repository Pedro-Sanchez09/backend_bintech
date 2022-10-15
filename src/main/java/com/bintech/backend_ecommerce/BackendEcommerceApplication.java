package com.bintech.backend_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendEcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendEcommerceApplication.class, args);
    }

    /**
     * Método que devuelve la configuración de CORS.
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**").allowedOrigins("http://localhost:3000").allowedHeaders("*").allowedMethods("*");
            }
        };
    }

}

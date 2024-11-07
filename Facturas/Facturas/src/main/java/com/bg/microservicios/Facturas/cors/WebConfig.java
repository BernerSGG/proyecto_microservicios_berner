package com.bg.microservicios.Facturas.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir CORS para todos los endpoints
                .allowedOrigins("http://localhost:8235", "http://localhost:8999","http://localhost:8236") // Ajusta según el origen de tu aplicación frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowCredentials(true) // Permitir credenciales (cookies, autorización)
                .maxAge(3600); // Tiempo máximo en segundos que el navegador puede almacenar la configuración CORS
    }
}
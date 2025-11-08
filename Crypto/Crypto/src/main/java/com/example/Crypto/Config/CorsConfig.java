package com.example.Crypto.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**") // Permite todas las rutas del backend
                            .allowedOrigins(
                                    "http://localhost:3000",  // Ejemplo: si tuvieras un frontend web
                                    "http://127.0.0.1",       // Para Swing o apps locales
                                    "http://localhost:8080"
                            )
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                            .allowedHeaders("*")
                            .allowCredentials(false)
                            .maxAge(3600);
                }
            };
        }
}



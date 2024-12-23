package com.server.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация клиента для настройки CORS.
 */
@Configuration
public class ClientConfig implements WebMvcConfigurer {

    /**
     * Настраивает CORS для всех запросов.
     *
     * @param registry реестр CORS для настройки
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}

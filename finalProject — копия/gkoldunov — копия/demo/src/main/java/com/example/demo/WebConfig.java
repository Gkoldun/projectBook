package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                           // Разрешить CORS для всех путей
                .allowedOrigins("http://localhost:3000")    // Разрешить запросы только с этого адреса фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешённые HTTP-методы
                .allowedHeaders("*")                         // Разрешить все заголовки
                .allowCredentials(true);                     // Разрешить отправлять куки и авторизационные заголовки
    }
}

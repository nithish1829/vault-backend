package com.example.vaultapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig
    implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(
        CorsRegistry registry
    ) {

        registry.addMapping("/**")

            // 🔥 FRONTEND URL
            .allowedOrigins(

                "http://localhost:5173",

                "https://your-frontend.vercel.app"
            )

            // 🔥 ALLOW METHODS
            .allowedMethods(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
            )

            // 🔥 ALLOW HEADERS
            .allowedHeaders("*")

            // 🔥 ALLOW JWT TOKEN
            .allowCredentials(true);
    }
}
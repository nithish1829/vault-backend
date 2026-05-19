package com.example.vaultapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter() {

        return new JwtFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http

            // 🔥 DISABLE CSRF
            .csrf(csrf -> csrf.disable())

            // 🔥 ENABLE CORS
            .cors(Customizer.withDefaults())

            // 🔥 STATELESS JWT
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            // 🔥 API RULES
            .authorizeHttpRequests(auth -> auth

                // 🔓 PUBLIC APIs
                .requestMatchers(
                    "/",
                    "/auth/**",
                    "/uploads/**"
                ).permitAll()

                // 🔒 PROTECTED APIs
                .requestMatchers(
                    "/files/**",
                    "/calc/**"
                ).authenticated()

                // 🔥 EVERYTHING ELSE
                .anyRequest().permitAll()
            )

            // 🔥 JWT FILTER
            .addFilterBefore(
                jwtFilter(),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
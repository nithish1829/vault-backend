package com.example.vaultapp.config;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtFilter
    extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(

        HttpServletRequest request,

        HttpServletResponse response,

        FilterChain filterChain

    )

        throws ServletException, IOException {

        String path =
            request.getServletPath();

        // 🔓 Skip login/register APIs
        if (
        	    path.startsWith("/auth") ||
        	    path.startsWith("/uploads") ||
        	    path.equals("/")
        	) {

        	    filterChain.doFilter(request, response);

        	    return;
        	}
        String authHeader =
            request.getHeader(
                "Authorization"
            );

        // ❌ No Token
        if (

            authHeader == null ||

            !authHeader.startsWith(
                "Bearer "
            )

        ) {

            response.setStatus(401);

            response.getWriter().write(
                "Unauthorized"
            );

            return;
        }

        // 🔥 Extract token
        String token =
            authHeader.substring(7);

        // 🔥 Validate token
        boolean valid =
            JwtUtil.validateToken(
                token
            );

        if (!valid) {

            response.setStatus(401);

            response.getWriter().write(
                "Invalid Token"
            );

            return;
        }

        // 🔥 Extract username
        String username =
            JwtUtil.extractUsername(
                token
            );

        // 🔥 SET AUTHENTICATION
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(

                username,

                null,

                Collections.emptyList()

            );

        SecurityContextHolder
            .getContext()
            .setAuthentication(auth);

        filterChain.doFilter(
            request,
            response
        );
    }
}
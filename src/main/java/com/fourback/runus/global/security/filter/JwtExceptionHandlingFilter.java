package com.fourback.runus.global.security.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtExceptionHandlingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtExceptionHandlingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            logger.error("Authentication error", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

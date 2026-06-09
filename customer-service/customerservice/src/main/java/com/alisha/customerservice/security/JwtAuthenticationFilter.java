package com.alisha.customerservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter
                extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final CustomerUserDetailsService userDetailsService;

        @Override
        protected void doFilterInternal(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");

                if (authHeader == null ||
                                !authHeader.startsWith("Bearer ")) {

                        log.warn(
                                        "Missing Authorization header for URI={}",
                                        request.getRequestURI());

                        filterChain.doFilter(request, response);
                        return;
                }

                String token = authHeader.substring(7);
                log.debug(
                                "JWT token received for URI={}",
                                request.getRequestURI());

                String username = jwtService.extractUsername(token);
                log.info(
                                "JWT validated successfully for user={}",
                                username);

                if (username != null &&
                                SecurityContextHolder.getContext()
                                                .getAuthentication() == null) {

                        UserDetails userDetails = userDetailsService
                                        .loadUserByUsername(username);

                        if (jwtService.isTokenValid(token)) {

                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                userDetails,
                                                null,
                                                userDetails.getAuthorities());

                                authToken.setDetails(
                                                new WebAuthenticationDetailsSource()
                                                                .buildDetails(request));

                                SecurityContextHolder
                                                .getContext()
                                                .setAuthentication(authToken);
                        }
                }

                filterChain.doFilter(request, response);
        }
}
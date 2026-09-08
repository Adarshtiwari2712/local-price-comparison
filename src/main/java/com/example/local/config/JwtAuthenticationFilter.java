package com.example.local.config;

import com.example.local.model.User;
import com.example.local.repository.UserRepository;
import com.example.local.service.JwtService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authHeader.substring(7);

            String email = jwtService.extractEmail(token);

            User user = userRepository.findByEmail(email)
                    .orElse(null);

            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(
                                            "ROLE_" + user.getRole()
                                    )
                            )
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {
            // Invalid or expired JWT
        }

        filterChain.doFilter(request, response);

    }
}
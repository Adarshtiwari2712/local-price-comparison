package com.example.local.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // =========================
                // CORS
                // =========================

                .cors(cors -> cors.configurationSource(request -> {

                    CorsConfiguration configuration =
                            new CorsConfiguration();

                    configuration.setAllowedOrigins(
                            List.of("http://localhost:63342")
                    );

                    configuration.setAllowedMethods(
                            List.of(
                                    "GET",
                                    "POST",
                                    "PUT",
                                    "DELETE",
                                    "OPTIONS"
                            )
                    );

                    configuration.setAllowedHeaders(
                            List.of("*")
                    );

                    configuration.setAllowCredentials(true);

                    return configuration;
                }))


                // =========================
                // CSRF
                // =========================

                .csrf(csrf -> csrf.disable())


                // =========================
                // SESSION
                // =========================

                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // =========================
                // DISABLE DEFAULT LOGIN
                // =========================

                .formLogin(form -> form.disable())

                .httpBasic(basic -> basic.disable())


                // =========================
                // AUTHORIZATION
                // =========================

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC AUTHENTICATION
                        // =========================

                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()


                        // =========================
                        // SHOPKEEPER POST
                        // =========================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/products",
                                "/stores",
                                "/prices"
                        ).hasRole("SHOPKEEPER")


                        // =========================
                        // SHOPKEEPER PUT
                        // =========================

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/products/**",
                                "/stores/**",
                                "/prices/**"
                        ).hasRole("SHOPKEEPER")


                        // =========================
                        // SHOPKEEPER DELETE
                        // =========================

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/products/**",
                                "/prices/**",
                                "/stores/**"
                        ).hasRole("SHOPKEEPER")


                        // =========================
                        // PUBLIC USER GET
                        // =========================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/products",
                                "/products/search",
                                "/prices",
                                "/prices/compare/name",
                                "/prices/compare/nearby",
                                "/stores"
                        ).permitAll()


                        // =========================
                        // EVERYTHING ELSE
                        // =========================

                        .anyRequest().authenticated()
                )


                // =========================
                // EXCEPTION HANDLING
                // =========================

                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint(
                                (request, response, authException) -> {

                                    response.setStatus(
                                            HttpServletResponse.SC_UNAUTHORIZED
                                    );

                                    response.setContentType(
                                            "application/json"
                                    );

                                    response.getWriter().write(
                                            "{\"error\":\"Authentication required\"}"
                                    );
                                }
                        )

                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {

                                    response.setStatus(
                                            HttpServletResponse.SC_FORBIDDEN
                                    );

                                    response.setContentType(
                                            "application/json"
                                    );

                                    response.getWriter().write(
                                            "{\"error\":\"Access denied\"}"
                                    );
                                }
                        )
                )


                // =========================
                // JWT FILTER
                // =========================

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
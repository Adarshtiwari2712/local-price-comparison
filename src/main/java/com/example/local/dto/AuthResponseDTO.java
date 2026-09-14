package com.example.local.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthResponseDTO {

    @Schema(
            description = "Unique ID of the shopkeeper",
            example = "2"
    )
    private Long id;

    @Schema(
            description = "Name of the shopkeeper",
            example = "Adarsh"
    )
    private String name;

    @Schema(
            description = "Email address of the shopkeeper",
            example = "shopkeeper@gmail.com"
    )
    private String email;

    @Schema(
            description = "Role assigned to the authenticated user",
            example = "SHOPKEEPER"
    )
    private String role;

    @Schema(
            description = "JWT token used to access protected APIs",
            example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String token;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(
            Long id,
            String name,
            String email,
            String role,
            String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
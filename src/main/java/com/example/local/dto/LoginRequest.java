package com.example.local.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @Schema(
            description = "Email address of the shopkeeper",
            example = "shopkeeper@gmail.com"
    )
    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(
            description = "Password of the shopkeeper",
            example = "********"
    )
    @NotBlank(message = "Password is required")
    private String password;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package com.example.local.controller;

import com.example.local.dto.AuthResponseDTO;
import com.example.local.dto.LoginRequest;
import com.example.local.dto.RegisterRequest;
import com.example.local.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponseDTO login(
            @Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }
}
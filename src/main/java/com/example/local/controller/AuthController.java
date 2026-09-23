package com.example.local.controller;

import com.example.local.dto.AuthResponseDTO;
import com.example.local.dto.LoginRequest;
import com.example.local.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.local.dto.ShopkeeperRegistrationRequest;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authentication",
        description = "APIs for shopkeeper authentication"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(
            summary = "Shopkeeper registration",
            description = "Registers a new shopkeeper and creates their local store"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Shopkeeper registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data"),
            @ApiResponse(responseCode = "409", description = "Email already registered")
    })
    @PostMapping("/register")
    public String register(
            @Valid @RequestBody ShopkeeperRegistrationRequest request) {
        return authService.register(request);
    }
    @Operation(
            summary = "Shopkeeper login",
            description = "Authenticates a shopkeeper and returns a JWT token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    @PostMapping("/login")
    public AuthResponseDTO login(
            @Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }

}
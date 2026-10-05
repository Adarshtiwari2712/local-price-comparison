package com.example.local.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    public UpdateProductRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
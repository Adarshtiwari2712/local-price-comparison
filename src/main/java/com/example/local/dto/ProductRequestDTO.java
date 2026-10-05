package com.example.local.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    public ProductRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
package com.example.local.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class ProductRequestDTO {

    @Schema(
            description = "Name of the product",
            example = "Milk"
    )
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(
            description = "Indicates whether the product is currently available",
            example = "true"
    )
    private boolean available;

    public ProductRequestDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}


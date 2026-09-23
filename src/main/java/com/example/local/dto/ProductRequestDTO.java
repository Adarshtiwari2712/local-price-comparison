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


    public ProductRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


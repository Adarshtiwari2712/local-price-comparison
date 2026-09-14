package com.example.local.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductResponseDTO {

    @Schema(
            description = "Unique ID of the product",
            example = "7"
    )
    private Long id;

    @Schema(
            description = "Name of the product",
            example = "Milk"
    )
    private String name;

    @Schema(
            description = "Indicates whether the product is currently available",
            example = "true"
    )
    private boolean available;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
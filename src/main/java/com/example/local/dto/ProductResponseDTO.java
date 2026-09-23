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


    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;

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


}
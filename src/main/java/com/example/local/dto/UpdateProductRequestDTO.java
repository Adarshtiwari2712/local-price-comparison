package com.example.local.dto;

import jakarta.validation.constraints.NotBlank;

@NotBlank(message = "Product name is required")
public class UpdateProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}

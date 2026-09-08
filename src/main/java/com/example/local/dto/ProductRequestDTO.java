package com.example.local.dto;
import jakarta.validation.constraints.NotBlank;

public class ProductRequestDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    private boolean available;

    public ProductRequestDTO(){}

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public boolean isAvailable(){
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
}

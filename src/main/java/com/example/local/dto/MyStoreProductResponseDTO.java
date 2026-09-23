package com.example.local.dto;

public class MyStoreProductResponseDTO {

    private String productName;
    private double price;
    private boolean available;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public MyStoreProductResponseDTO(
            String productName,
            double price,
            boolean available
    )
    {
        this.productName = productName;
        this.price = price;
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;

    }
}

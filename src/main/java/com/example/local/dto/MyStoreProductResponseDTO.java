package com.example.local.dto;

public class MyStoreProductResponseDTO {

    private Long productId;
    private String productName;
    private double price;
    private boolean available;

    public MyStoreProductResponseDTO() {
    }

    public MyStoreProductResponseDTO(
            Long productId,
            String productName,
            double price,
            boolean available
    ) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.available = available;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
package com.example.local.dto;

import jakarta.validation.constraints.Positive;

public class PriceRequestDTO {

    @Positive(message = "Amount must be greater than zero")
    private double amount;

    private boolean available;

    private Long productId;

    private Long storeId;

    public PriceRequestDTO() {
    }

    public double getAmount() {
        return amount;
    }

    public boolean isAvailable() {
        return available;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
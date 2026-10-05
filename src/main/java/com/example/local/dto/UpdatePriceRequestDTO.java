package com.example.local.dto;

import jakarta.validation.constraints.Positive;

public class UpdatePriceRequestDTO {

    @Positive(message = "Amount must be greater than zero")
    private double amount;

    private boolean available;

    public UpdatePriceRequestDTO() {
    }

    public double getAmount() {
        return amount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
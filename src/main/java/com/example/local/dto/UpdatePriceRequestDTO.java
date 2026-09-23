package com.example.local.dto;

import jakarta.validation.constraints.Positive;

public class UpdatePriceRequestDTO {

    @Positive(message = "Price must be greater than 0")
    private double amount;
    private boolean available;

    public UpdatePriceRequestDTO(
            double amount, boolean available){
        this.amount = amount;
        this.available = available;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}



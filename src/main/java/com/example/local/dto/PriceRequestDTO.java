package com.example.local.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PriceRequestDTO {
    @Positive(message = "Price must be greater than 0")
    private double amount;
    @NotNull(message = "Product ID is required")
    private Long productId;
    @NotNull(message = "Store ID is required")
    private Long storeId;

    public PriceRequestDTO() {
    }
        public double getAmount(){
            return amount;
        }
        public void setAmount(double amount){
        this.amount = amount;
        }
        public Long getProductId(){
        return productId;
        }
        public void setProductId(Long productId){
        this.productId = productId;
        }
        public Long getStoreId(){
        return storeId;
        }
        public void setStoreId(Long storeId){
        this.storeId = storeId;
    }

}

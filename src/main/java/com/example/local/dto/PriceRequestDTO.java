package com.example.local.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;

public class PriceRequestDTO {

    @Schema(
            description = "Price of the product",
            example = "50.0"
    )
    @Positive(message = "Price must be greater than 0")
    private double amount;
    @Schema(
            description = "ID of the product for which the price is being added",
            example = "7"
    )
    @NotNull(message = "Product ID is required")
    private Long productId;
    @Schema(
            description = "ID of the store where the product is being sold",
            example = "15"
    )
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

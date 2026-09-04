package com.example.local.dto;

public class PriceResponseDTO {

    private Long id;
    private double amount;
    private Long productId;
    private String productName;
    private Long storeId;
    private String storeName;

    public PriceResponseDTO() {
    }

    public PriceResponseDTO(
            Long id,
            double amount,
            Long productId,
            String productName,
            Long storeId,
            String storeName) {

        this.id = id;
        this.amount = amount;
        this.productId = productId;
        this.productName = productName;
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
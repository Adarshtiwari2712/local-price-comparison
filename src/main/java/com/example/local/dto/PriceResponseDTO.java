package com.example.local.dto;

public class PriceResponseDTO {

    private Long id;
    private double amount;
    private boolean available;

    private Long productId;
    private String productName;

    private Long storeId;
    private String storeName;
    private String storeAddress;
    private String storePhone;

    private double distanceKm;

    public PriceResponseDTO() {
    }

    public PriceResponseDTO(
            Long id,
            double amount,
            boolean available,
            Long productId,
            String productName,
            Long storeId,
            String storeName
    ) {
        this.id = id;
        this.amount = amount;
        this.available = available;
        this.productId = productId;
        this.productName = productName;
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public PriceResponseDTO(
            Long id,
            double amount,
            boolean available,
            Long productId,
            String productName,
            Long storeId,
            String storeName,
            String storeAddress,
            String storePhone
    ) {
        this.id = id;
        this.amount = amount;
        this.available = available;
        this.productId = productId;
        this.productName = productName;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
    }

    public PriceResponseDTO(
            Long id,
            double amount,
            boolean available,
            Long productId,
            String productName,
            Long storeId,
            String storeName,
            String storeAddress,
            String storePhone,
            double distanceKm
    ) {
        this.id = id;
        this.amount = amount;
        this.available = available;
        this.productId = productId;
        this.productName = productName;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.distanceKm = distanceKm;
    }

    public Long getId() {
        return id;
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

    public String getProductName() {
        return productName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }
}
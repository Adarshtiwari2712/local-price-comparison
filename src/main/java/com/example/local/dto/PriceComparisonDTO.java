package com.example.local.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PriceComparisonDTO {

    @Schema(
            description = "Name of the product being compared",
            example = "Milk"
    )
    private String productName;

    @Schema(
            description = "List of prices available for the product across different stores"
    )
    private List<PriceResponseDTO> prices;

    @Schema(
            description = "Name of the store offering the cheapest price",
            example = "Ravi Store"
    )
    private String cheapestStore;

    @Schema(
            description = "Cheapest available price of the product",
            example = "45.0"
    )
    private double cheapestPrice;

    public PriceComparisonDTO(
            String productName,
            List<PriceResponseDTO> prices,
            String cheapestStore,
            double cheapestPrice
    ) {
        this.productName = productName;
        this.prices = prices;
        this.cheapestPrice = cheapestPrice;
        this.cheapestStore = cheapestStore;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<PriceResponseDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceResponseDTO> prices) {
        this.prices = prices;
    }

    public String getCheapestStore() {
        return cheapestStore;
    }

    public void setCheapestStore(String cheapestStore) {
        this.cheapestStore = cheapestStore;
    }

    public double getCheapestPrice() {
        return cheapestPrice;
    }

    public void setCheapestPrice(double cheapestPrice) {
        this.cheapestPrice = cheapestPrice;
    }
}
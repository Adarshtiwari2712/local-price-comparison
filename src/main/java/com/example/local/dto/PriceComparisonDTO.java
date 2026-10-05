package com.example.local.dto;

import java.util.List;

public class PriceComparisonDTO {

    private String productName;
    private double cheapestPrice;
    private List<PriceResponseDTO> prices;

    public PriceComparisonDTO() {
    }

    public PriceComparisonDTO(
            String productName,
            double cheapestPrice,
            List<PriceResponseDTO> prices
    ) {
        this.productName = productName;
        this.cheapestPrice = cheapestPrice;
        this.prices = prices;
    }

    public String getProductName() {
        return productName;
    }

    public double getCheapestPrice() {
        return cheapestPrice;
    }

    public List<PriceResponseDTO> getPrices() {
        return prices;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCheapestPrice(double cheapestPrice) {
        this.cheapestPrice = cheapestPrice;
    }

    public void setPrices(List<PriceResponseDTO> prices) {
        this.prices = prices;
    }
}
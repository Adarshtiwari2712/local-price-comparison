package com.example.local.dto;

import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.List;



public class PriceComparisonDTO {
    private String productName;
    private List<PriceResponseDTO> prices;
    private String cheapestStore;
    private double cheapestPrice;

    public PriceComparisonDTO(
            String productName,
            List<PriceResponseDTO> prices,
            String cheapestStore,
            double cheapestPrice
    ){
        this.productName = productName;
        this.prices = prices;
        this.cheapestPrice = cheapestPrice;
        this.cheapestStore = cheapestStore;
    }
    public String getProductName(){
        return  productName;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public List<PriceResponseDTO> getPrices(){
        return prices;
    }
    public void setPrices(List<PriceResponseDTO> prices){
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



package com.example.local.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "prices",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_price_product_store",
                        columnNames = {"product_id", "store_id"}
                )
        }
)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private LocalStore store;

    public Price() {
    }

    public Price(
            double amount,
            boolean available,
            Product product,
            LocalStore store
    ) {
        this.amount = amount;
        this.available = available;
        this.product = product;
        this.store = store;
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

    public Product getProduct() {
        return product;
    }

    public LocalStore getStore() {
        return store;
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

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStore(LocalStore store) {
        this.store = store;
    }
}
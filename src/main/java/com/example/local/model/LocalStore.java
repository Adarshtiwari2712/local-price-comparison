package com.example.local.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "local_stores")
public class LocalStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(length = 10)
    private String phone;

    // Store location
    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private User owner;

    @OneToMany(
            mappedBy = "store",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> products = new ArrayList<>();

    @OneToMany(
            mappedBy = "store",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Price> prices = new ArrayList<>();

    public LocalStore() {
    }
    public LocalStore(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public LocalStore(
            String name,
            String address,
            String phone,
            double latitude,
            double longitude
    ) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public User getOwner() {
        return owner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }
}
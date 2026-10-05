package com.example.local.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "store_id"})
        }
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private LocalStore store;


    // Default constructor
    public Product() {
    }


    // Constructor
    public Product(String name, LocalStore store) {
        this.name = name;
        this.store = store;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LocalStore getStore() {
        return store;
    }

    public void setStore(LocalStore store) {
        this.store = store;
    }
}
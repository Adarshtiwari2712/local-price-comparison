package com.example.local.model;
import jakarta.persistence.*;
import com.example.local.model.Product;
 @Entity
 @Table(name = "prices")

public class Price {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private double amount;

     @ManyToOne
     @JoinColumn(name = "product_id")
     private Product product;

     public LocalStore getStore() {
         return store;
     }

     public void setStore(LocalStore store) {
         this.store = store;
     }

     @ManyToOne
     @JoinColumn(name = "store_id")
     private LocalStore store;

     public Price(){

     }
     public Price(double amount){
         this.amount = amount;
     }

     public Product getProduct() {
         return product;
     }

     public void setProduct(Product product) {
         this.product = product;
     }

     public Long getId(){
         return id;
     }
     public void setId(Long id){
         this.id = id;
     }
     public double getAmount(){
         return amount;
     }
     public void setAmount(double amount){
         this.amount = amount;
     }
}

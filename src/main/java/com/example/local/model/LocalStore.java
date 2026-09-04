package com.example.local.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.ArrayList;
import jakarta.validation.constraints.Pattern;

@Entity
 @Table(name = "local_stores")

public class LocalStore {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @NotBlank(message = "Store name is required")
     private String name;

     @NotBlank(message = "Store address is required")
     private String address;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phone;

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    @OneToMany(mappedBy = "store")
    private List<Price> prices = new ArrayList<>();

     public LocalStore() {
     }
         public LocalStore(String name, String address, String phone){
             this.name = name;
             this.address = address;
             this.phone = phone;


         }

         public Long getId(){
         return id;
         }

         public void setId(Long id){
         this.id = id;
         }

         public String getName(){
         return name;
         }

         public void setName(String name){
         this.name = name;
         }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }

     public String getPhone() {
         return phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }
 }

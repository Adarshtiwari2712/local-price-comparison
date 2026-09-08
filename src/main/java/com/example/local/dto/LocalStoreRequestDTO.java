package com.example.local.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class LocalStoreRequestDTO {

    @NotBlank(message = "Store name is required")
    private String name;

    @NotBlank(message = "Store address is required")
    private String address;


    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )

    private String phone;

    public LocalStoreRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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




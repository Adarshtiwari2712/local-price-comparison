package com.example.local.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateStoreRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;
    @Pattern(regexp = "^[0-9]{10}$",
             message = "phone number must contain exactly 10 digits"
    )
    private String phone;

    public UpdateStoreRequestDTO() {
    }

public UpdateStoreRequestDTO(
        String name, String address, String phone){
    this.name = name;
    this.address = address;
    this.phone = phone;
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

package com.example.local.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LocalStoreResponseDTO {

    @Schema(
            description = "Unique ID of the store",
            example = "15"
    )
    private Long id;

    @Schema(
            description = "Name of the local store",
            example = "Gupta Store"
    )
    private String name;

    @Schema(
            description = "Address of the local store",
            example = "Main Market, Agra"
    )
    private String address;

    @Schema(
            description = "10-digit phone number of the store",
            example = "9876543210"
    )
    private String phone;

    public LocalStoreResponseDTO() {
    }

    public LocalStoreResponseDTO(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

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
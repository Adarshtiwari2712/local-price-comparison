package com.example.local.controller;

import com.example.local.dto.*;
import com.example.local.service.PriceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(
            PriceService priceService
    ) {
        this.priceService = priceService;
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public PriceResponseDTO addPrice(
            @Valid @RequestBody PriceRequestDTO request
    ) {
        return priceService.addPrice(request);
    }

    @GetMapping
    public List<PriceResponseDTO> getAllPrices() {
        return priceService.getAllPrices();
    }

    @GetMapping("/my-store")
    @SecurityRequirement(name = "bearerAuth")
    public List<PriceResponseDTO> getMyStorePrices() {
        return priceService.getMyStorePrices();
    }

    // ==========================================
    // NORMAL COMPARISON
    // ==========================================

    @GetMapping("/compare/name")
    public PriceComparisonDTO comparePricesByName(
            @RequestParam String name
    ) {
        return priceService.comparePricesByName(name);
    }

    // ==========================================
    // NEARBY COMPARISON - WITHIN 5 KM
    // ==========================================

    @GetMapping("/compare/nearby")
    public PriceComparisonDTO compareNearbyPrices(

            @RequestParam String name,

            @RequestParam double latitude,

            @RequestParam double longitude

    ) {

        return priceService.compareNearbyPrices(
                name,
                latitude,
                longitude
        );
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public PriceResponseDTO updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePriceRequestDTO request
    ) {

        return priceService.updatePrice(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public String deletePrice(
            @PathVariable Long id
    ) {

        priceService.deletePrice(id);

        return "Price deleted successfully";
    }
}
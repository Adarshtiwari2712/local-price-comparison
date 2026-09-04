package com.example.local.controller;

import com.example.local.dto.PriceRequestDTO;
import com.example.local.dto.PriceResponseDTO;
import com.example.local.model.Price;
import com.example.local.service.PriceService;
import org.springframework.web.bind.annotation.*;
import com.example.local.dto.PriceComparisonDTO;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    // Add a new price
    @PostMapping
    public PriceResponseDTO addPrice( @Valid @RequestBody PriceRequestDTO request) {
        Price price = priceService.addPrice(request);

        return new PriceResponseDTO(
                price.getId(),
                price.getAmount(),
                price.getProduct().getId(),
                price.getProduct().getName(),
                price.getStore().getId(),
                price.getStore().getName()
        );
    }

    // Get all prices
    @GetMapping
    public List<PriceResponseDTO> getAllPrices() {
        return priceService.getAllPrices();
    }

    // compare prices and find the cheapest store

    @GetMapping("/compare/name")
    public PriceComparisonDTO comparePricesByName(@RequestParam String name) {
        return priceService.comparePricesByName(name);
    }
}
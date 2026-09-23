package com.example.local.controller;

import com.example.local.dto.PriceRequestDTO;
import com.example.local.dto.PriceResponseDTO;
import com.example.local.dto.UpdatePriceRequestDTO;
import com.example.local.model.Price;
import com.example.local.service.PriceService;
import org.springframework.web.bind.annotation.*;
import com.example.local.dto.PriceComparisonDTO;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/prices")
@Tag(
        name = "Prices",
        description = "APIs for managing and comparing product prices"
)
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(
            summary = "Add or update a product price",
            description = "Adds a new price or updates the existing price for a product in a shopkeeper's own store"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price added or updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid price data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Product or store not found")
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public PriceResponseDTO addPrice(@Valid @RequestBody PriceRequestDTO request) {
        Price price = priceService.addPrice(request);

        return new PriceResponseDTO(
                price.getId(),
                price.getAmount(),
                price.isAvailable(),
                price.getProduct().getId(),
                price.getProduct().getName(),
                price.getStore().getId(),
                price.getStore().getName()
        );
    }

    @Operation(
            summary = "Get all prices",
            description = "Returns all product prices from local stores"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prices retrieved successfully")
    })
    @GetMapping
    public List<PriceResponseDTO> getAllPrices() {
        return priceService.getAllPrices();
    }

    // Get prices of authenticated shopkeeper's store
    @Operation(
            summary = "Get my store prices",
            description = "Returns all prices managed by the authenticated shopkeeper"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prices retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "404", description = "Shopkeeper does not have a store")
    })
    @GetMapping("/my-store")
    @SecurityRequirement(name = "bearerAuth")
    public List<PriceResponseDTO> getMyStorePrices() {
        return priceService.getMyStorePrices();
    }

    @Operation(
            summary = "Compare prices by product name",
            description = "Compares prices of a product across different local stores and identifies the available prices"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price comparison retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No prices available for the requested product")
    })
    @GetMapping("/compare/name")
    public PriceComparisonDTO comparePricesByName(@RequestParam String name) {
        return priceService.comparePricesByName(name);
    }

    @Operation(
            summary = "Delete a price",
            description = "Deletes a price only if it belongs to the authenticated shopkeeper's own store"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Price not found")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public String deletePrice(@PathVariable Long id) {
        priceService.deletePrice(id);
        return "Price deleted successfully";
    }


    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public PriceResponseDTO updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePriceRequestDTO request){

        Price price = priceService.updatePrice(id,request);

       return new PriceResponseDTO(
               price.getId(),
               price.getAmount(),
               price.isAvailable(),
               price.getProduct().getId(),
               price.getProduct().getName(),
               price.getStore().getId(),
               price.getStore().getName()
       ) ;
    }

}



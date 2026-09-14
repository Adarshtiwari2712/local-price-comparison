package com.example.local.controller;

import com.example.local.dto.LocalStoreRequestDTO;
import com.example.local.dto.LocalStoreResponseDTO;
import com.example.local.service.LocalStoreService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/stores")
@Tag(
        name = "Stores",
        description = "APIs for managing local stores"
)
public class LocalStoreController {
    private final LocalStoreService localStoreService;

    public LocalStoreController(LocalStoreService localStoreService){
        this.localStoreService = localStoreService;

    }
    @Operation(
            summary = "Add a local store",
            description = "Creates a local store for the authenticated shopkeeper"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Store added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid store data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "409", description = "Shopkeeper already has a store")
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public LocalStoreResponseDTO addStore(@Valid @RequestBody LocalStoreRequestDTO request){
        return localStoreService.addStore(request);
    }
    @Operation(
            summary = "Get all local stores",
            description = "Returns a list of all local stores registered in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stores retrieved successfully")
    })
    @GetMapping
    public List<LocalStoreResponseDTO> getAllStores(){
        return localStoreService.getAllStores();
    }
    @Operation(
            summary = "Delete a local store",
            description = "Deletes a store only if it belongs to the authenticated shopkeeper and has no associated prices"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Store deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied or store does not belong to the shopkeeper"),
            @ApiResponse(responseCode = "404", description = "Store not found"),
            @ApiResponse(responseCode = "409", description = "Store cannot be deleted because prices exist")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public String deleteStore(@PathVariable Long id) {
        localStoreService.deleteStore(id);
        return "Store deleted successfully";
    }

}


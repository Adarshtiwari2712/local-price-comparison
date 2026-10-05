package com.example.local.controller;

import com.example.local.dto.LocalStoreRequestDTO;
import com.example.local.dto.LocalStoreResponseDTO;
import com.example.local.dto.UpdateStoreRequestDTO;
import com.example.local.model.LocalStore;
import com.example.local.service.LocalStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@Tag(
        name = "Stores",
        description = "APIs for managing local stores"
)
public class LocalStoreController {

    private final LocalStoreService localStoreService;

    public LocalStoreController(
            LocalStoreService localStoreService
    ) {
        this.localStoreService = localStoreService;
    }

    @Operation(
            summary = "Add a local store",
            description = "Creates a local store for the authenticated shopkeeper"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Store added successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid store data"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Shopkeeper already has a store"
            )
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public LocalStoreResponseDTO addStore(
            @Valid @RequestBody LocalStoreRequestDTO request
    ) {
        return localStoreService.addStore(request);
    }

    @Operation(
            summary = "Get my store",
            description = "Returns the store belonging to the authenticated shopkeeper"
    )
    @GetMapping("/my-store")
    @SecurityRequirement(name = "bearerAuth")
    public LocalStoreResponseDTO getMyStore() {
        return localStoreService.getMyStore();
    }

    @Operation(
            summary = "Get all local stores"
    )
    @GetMapping
    public List<LocalStoreResponseDTO> getAllStores() {
        return localStoreService.getAllStores();
    }

    @Operation(
            summary = "Delete a local store"
    )
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public String deleteStore(
            @PathVariable Long id
    ) {

        localStoreService.deleteStore(id);

        return "Store deleted successfully";
    }

    @Operation(
            summary = "Update my store"
    )
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public LocalStoreResponseDTO updateStore(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStoreRequestDTO request
    ) {

        LocalStore store =
                localStoreService.updateStore(id, request);

        return new LocalStoreResponseDTO(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getPhone(),
                store.getLatitude(),
                store.getLongitude()
        );
    }
}
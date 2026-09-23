package com.example.local.controller;

import com.example.local.dto.ProductRequestDTO;
import com.example.local.dto.ProductResponseDTO;
import com.example.local.dto.UpdateProductRequestDTO;
import com.example.local.model.Product;
import com.example.local.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.local.dto.MyStoreProductResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(
        name = "Products",
        description = "APIs for managing products"
)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @Operation(
            summary = "Add a new product",
            description = "Adds a product to the local price comparison system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "409", description = "Product already exists")
    })
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO request) {
        return productService.addProduct(request);
    }
    @Operation(
            summary = "Get all products",
            description = "Returns a list of all products available in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }
    @Operation(
            summary = "Search for a product",
            description = "Searches for a product by its name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/search")
    public ProductResponseDTO searchProduct(@RequestParam String name) {
        return productService.searchProduct(name);
    }
    @Operation(
            summary = "Delete a product",
            description = "Deletes a product if no prices are associated with it"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "409", description = "Product cannot be deleted because prices exist")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    @Operation(
            summary = "Update a product price",
            description = "Updates the price and availability of a product in the authenticated shopkeeper's own store"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Price updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid price data"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Price not found"
            )
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ProductResponseDTO updateProductName(
            @PathVariable Long id,
           @Valid @RequestBody UpdateProductRequestDTO request){
         Product product = productService.updateProductName(id, request);

         return new ProductResponseDTO(
                 product.getId(),
                 product.getName()
         );


    }
    @Operation(
            summary = "Get my store products",
            description = "Returns all products available in the authenticated shopkeeper's store"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "404", description = "Shopkeeper does not have a store")
    })
    @GetMapping("/my-store")
    @SecurityRequirement(name = "bearerAuth")
    public List<MyStoreProductResponseDTO> getMyStoreProducts() {
        return productService.getMyStoreProducts();
    }
}
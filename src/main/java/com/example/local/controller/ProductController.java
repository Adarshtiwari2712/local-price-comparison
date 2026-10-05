package com.example.local.controller;

import com.example.local.dto.MyStoreProductResponseDTO;
import com.example.local.dto.ProductRequestDTO;
import com.example.local.dto.ProductResponseDTO;
import com.example.local.dto.UpdateProductRequestDTO;
import com.example.local.model.Product;
import com.example.local.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // -----------------------------------------
    // Add product to my store
    // -----------------------------------------

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ProductResponseDTO addProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {
        return productService.addProduct(request);
    }

    // -----------------------------------------
    // Get all products
    // -----------------------------------------

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    // -----------------------------------------
    // Search products
    // -----------------------------------------

    @GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(
            @RequestParam String name
    ) {
        return productService.searchProducts(name);
    }

    // -----------------------------------------
    // My store products
    // -----------------------------------------

    @GetMapping("/my-store")
    @SecurityRequirement(name = "bearerAuth")
    public List<MyStoreProductResponseDTO>
    getMyStoreProducts() {

        return productService.getMyStoreProducts();
    }

    // -----------------------------------------
    // Update product
    // -----------------------------------------

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ProductResponseDTO updateProductName(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequestDTO request
    ) {

        Product product =
                productService.updateProductName(
                        id,
                        request
                );

        return new ProductResponseDTO(
                product.getId(),
                product.getName()
        );
    }

    // -----------------------------------------
    // Delete product
    // -----------------------------------------

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public String deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }
}
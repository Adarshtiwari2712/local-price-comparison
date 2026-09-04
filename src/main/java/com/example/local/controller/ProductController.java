package com.example.local.controller;

import com.example.local.dto.ProductResponseDTO;
import com.example.local.model.Product;
import com.example.local.service.ProductService;
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

    @PostMapping
    public ProductResponseDTO addProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public ProductResponseDTO searchProduct(@RequestParam String name) {
        return productService.searchProduct(name);
    }
}
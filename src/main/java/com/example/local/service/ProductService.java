package com.example.local.service;

import com.example.local.dto.ProductResponseDTO;
import com.example.local.exception.ProductAlreadyExistsException;
import com.example.local.model.Product;
import com.example.local.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.local.exception.ProductNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO addProduct(Product product) {

        if (productRepository.existsByNameIgnoreCase(product.getName())) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.isAvailable()
        );
    }

    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.isAvailable()
                ))
                .toList();
    }

    public ProductResponseDTO searchProduct(String name) {

        Product product = productRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.isAvailable()
        );
    }
}


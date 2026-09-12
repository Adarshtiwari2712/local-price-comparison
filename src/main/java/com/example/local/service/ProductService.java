package com.example.local.service;

import com.example.local.dto.ProductRequestDTO;
import com.example.local.dto.ProductResponseDTO;
import com.example.local.exception.ProductAlreadyExistsException;
import com.example.local.exception.ProductHasPricesException;
import com.example.local.model.Product;
import com.example.local.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.local.exception.ProductNotFoundException;
import com.example.local.exception.NoPricesAvailableException;
import com.example.local.model.Price;
import com.example.local.repository.PriceRepository;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final PriceRepository priceRepository;

    public ProductService(ProductRepository productRepository,
                          PriceRepository priceRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    public ProductResponseDTO addProduct(ProductRequestDTO request) {
        Product product = new Product(
                request.getName(),
                request.isAvailable()
        );

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

    public void deleteProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->
                        new ProductNotFoundException(
                                "Product not found"
                        ));
        if(priceRepository.existsByProduct(product)){
            throw new ProductHasPricesException(
                    "Cannot delete product because prices exist for it"
            );
        }
        productRepository.delete(product);
    }
}


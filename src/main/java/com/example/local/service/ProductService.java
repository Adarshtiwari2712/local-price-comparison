package com.example.local.service;

import com.example.local.dto.MyStoreProductResponseDTO;
import com.example.local.dto.ProductRequestDTO;
import com.example.local.dto.ProductResponseDTO;
import com.example.local.dto.UpdateProductRequestDTO;
import com.example.local.exception.ProductAlreadyExistsException;
import com.example.local.exception.ProductHasPricesException;
import com.example.local.exception.ProductNotFoundException;
import com.example.local.model.LocalStore;
import com.example.local.model.Price;
import com.example.local.model.Product;
import com.example.local.model.User;
import com.example.local.repository.LocalStoreRepository;
import com.example.local.repository.PriceRepository;
import com.example.local.repository.ProductRepository;
import com.example.local.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final LocalStoreRepository localStoreRepository;
    private final UserRepository userRepository;

    public ProductService(
            ProductRepository productRepository,
            PriceRepository priceRepository,
            LocalStoreRepository localStoreRepository,
            UserRepository userRepository
    ) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.localStoreRepository = localStoreRepository;
        this.userRepository = userRepository;
    }

    // -----------------------------------------
    // Get logged-in shopkeeper
    // -----------------------------------------

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // -----------------------------------------
    // Get logged-in shopkeeper's store
    // -----------------------------------------

    private LocalStore getMyStore() {

        User user = getLoggedInUser();

        return localStoreRepository.findByOwner(user)
                .orElseThrow(() ->
                        new RuntimeException("Store not found"));
    }

    // -----------------------------------------
    // Add Product
    // -----------------------------------------

    public ProductResponseDTO addProduct(
            ProductRequestDTO request
    ) {

        LocalStore store = getMyStore();

        String name = request.getName().trim();

        /*
         * Check only inside THIS shop.
         *
         * Therefore:
         *
         * Gupta Store -> Milk
         * Ravi Store  -> Milk
         *
         * are allowed.
         */
        if (productRepository
                .findByNameIgnoreCaseAndStoreId(
                        name,
                        store.getId()
                )
                .isPresent()) {

            throw new ProductAlreadyExistsException(
                    "Product already exists in your store"
            );
        }

        Product product = new Product();

        product.setName(name);
        product.setStore(store);

        Product savedProduct =
                productRepository.save(product);

        return new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName()
        );
    }

    // -----------------------------------------
    // Get all products
    // -----------------------------------------

    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product ->
                        new ProductResponseDTO(
                                product.getId(),
                                product.getName()
                        )
                )
                .toList();
    }

    // -----------------------------------------
    // Search products globally
    // -----------------------------------------

    public List<ProductResponseDTO> searchProducts(
            String name
    ) {

        return productRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(product ->
                        new ProductResponseDTO(
                                product.getId(),
                                product.getName()
                        )
                )
                .toList();
    }

    // -----------------------------------------
    // Get my store products
    // -----------------------------------------

    public List<MyStoreProductResponseDTO>
    getMyStoreProducts() {

        LocalStore store = getMyStore();

        List<Product> products =
                productRepository.findByStoreId(
                        store.getId()
                );

        return products.stream()
                .map(product -> {

                    Price price =
                            priceRepository
                                    .findByProductIdAndStoreId(
                                            product.getId(),
                                            store.getId()
                                    )
                                    .orElse(null);

                    double amount =
                            price != null
                                    ? price.getAmount()
                                    : 0;

                    boolean available =
                            price != null &&
                                    price.isAvailable();

                    return new MyStoreProductResponseDTO(
                            product.getId(),
                            product.getName(),
                            amount,
                            available
                    );
                })
                .toList();
    }

    // -----------------------------------------
    // Update my product
    // -----------------------------------------

    public Product updateProductName(
            Long id,
            UpdateProductRequestDTO request
    ) {

        LocalStore store = getMyStore();

        Product product =
                productRepository
                        .findByIdAndStoreId(
                                id,
                                store.getId()
                        )
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found in your store"
                                )
                        );

        String newName =
                request.getName().trim();

        /*
         * Prevent duplicate names
         * only within the same store.
         */
        productRepository
                .findByNameIgnoreCaseAndStoreId(
                        newName,
                        store.getId()
                )
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new ProductAlreadyExistsException(
                                "Product with this name already exists in your store"
                        );
                    }
                });

        product.setName(newName);

        return productRepository.save(product);
    }

    // -----------------------------------------
    // Delete my product
    // -----------------------------------------

    public void deleteProduct(Long id) {

        LocalStore store = getMyStore();

        Product product =
                productRepository
                        .findByIdAndStoreId(
                                id,
                                store.getId()
                        )
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found in your store"
                                )
                        );

        /*
         * Price belongs to this product.
         */
        if (priceRepository
                .findByProductIdAndStoreId(
                        id,
                        store.getId()
                )
                .isPresent()) {

            throw new ProductHasPricesException(
                    "Delete the product price before deleting the product"
            );
        }

        productRepository.delete(product);
    }
}
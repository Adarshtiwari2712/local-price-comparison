package com.example.local.service;

import com.example.local.dto.MyStoreProductResponseDTO;
import com.example.local.dto.ProductRequestDTO;
import com.example.local.dto.ProductResponseDTO;
import com.example.local.dto.UpdateProductRequestDTO;
import com.example.local.exception.*;
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
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final UserRepository userRepository;
    private final LocalStoreRepository localStoreRepository;

    public ProductService(
            ProductRepository productRepository,
            PriceRepository priceRepository,
            UserRepository userRepository,
            LocalStoreRepository localStoreRepository) {

        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.userRepository = userRepository;
        this.localStoreRepository = localStoreRepository;
    }

    public ProductResponseDTO addProduct(ProductRequestDTO request) {

        Product product = new Product(
                request.getName()

            );


        if (productRepository.existsByNameIgnoreCase(product.getName())) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName()
        );
    }

    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName()
                ))
                .toList();
    }

    public ProductResponseDTO searchProduct(String name) {

        Product product = productRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        return new ProductResponseDTO(
                product.getId(),
                product.getName()
        );
    }

    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        if (priceRepository.existsByProduct(product)) {
            throw new ProductHasPricesException(
                    "Cannot delete product because prices exist for it"
            );
        }

        productRepository.delete(product);
    }

    public List<MyStoreProductResponseDTO> getMyStoreProducts() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Shopkeeper not found"
                        ));

        LocalStore store = localStoreRepository.findByOwner(owner)
                .orElseThrow(() ->
                        new StoreNotFoundException(
                                "Shopkeeper does not have a store"
                        ));

        List<Price> prices = priceRepository.findByStore(store);

        return prices.stream()
                .map(price -> new MyStoreProductResponseDTO(
                        price.getProduct().getName(),
                        price.getAmount(),
                        price.isAvailable()
                ))
                .toList();
    }

    public Product updateProductName(Long productId, UpdateProductRequestDTO request){

        Product product = productRepository.findById(productId)
                .orElseThrow(()->
                        new ProductNotFoundException("Product not found"));
         Authentication authentication =
         SecurityContextHolder.getContext().getAuthentication();

         String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new InvalidCredentialsException("Shopkeeper not found"));

        LocalStore ownerStore = localStoreRepository.findByOwner(owner)
                .orElseThrow(()->
                        new StoreNotFoundException(
                                "Shopkeeper does not have a store"
                        ));

        List<Price> prices = priceRepository.findByProduct(product);

        boolean belongsToOwner = prices.stream()
                .anyMatch(price ->
                        price.getStore().getId().equals(ownerStore.getId()));

        if(!belongsToOwner){
            throw new AccessDeniedException(
                    "You can only update products in your own store"
            );
        }
        product.setName(request.getName());
        return productRepository.save(product);

    }
}
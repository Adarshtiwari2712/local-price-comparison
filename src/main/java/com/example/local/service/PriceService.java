package com.example.local.service;

import com.example.local.dto.PriceComparisonDTO;
import com.example.local.dto.PriceRequestDTO;
import com.example.local.dto.PriceResponseDTO;
import com.example.local.exception.InvalidCredentialsException;
import com.example.local.exception.NoPricesAvailableException;
import com.example.local.exception.ProductNotFoundException;
import com.example.local.exception.StoreNotFoundException;
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
import com.example.local.exception.PriceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final LocalStoreRepository localStoreRepository;
    private final UserRepository userRepository;

    public PriceService(
            PriceRepository priceRepository,
            ProductRepository productRepository,
            LocalStoreRepository localStoreRepository,
            UserRepository userRepository) {

        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.localStoreRepository = localStoreRepository;
        this.userRepository = userRepository;
    }

    // Add a new price
    public Price addPrice(PriceRequestDTO request) throws AccessDeniedException {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        LocalStore store = localStoreRepository.findById(request.getStoreId())
                .orElseThrow(() ->
                        new StoreNotFoundException("Store not found"));

        // Get logged-in shopkeeper
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Shopkeeper not found"
                        ));

        LocalStore ownerStore = localStoreRepository.findByOwner(owner)
                .orElseThrow(() ->
                        new StoreNotFoundException(
                                "Shopkeeper does not have a store"
                        ));

        if(!ownerStore.getId().equals(store.getId())){
            throw new AccessDeniedException(
                    "You can only manage prices for your own store"
            );
        }

        // Check if price already exists for this product and store
        Price price = priceRepository.findByProductAndStore(product, store)
                .orElse(new Price());

        price.setAmount(request.getAmount());
        price.setProduct(product);
        price.setStore(store);

        return priceRepository.save(price);
    }

    // Compare prices and find the cheapest store
    public PriceComparisonDTO comparePrices(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        List<Price> prices = priceRepository.findByProduct(product);

        if (prices.isEmpty()) {
            throw new NoPricesAvailableException(
                    "No prices available for this product");
        }

        List<PriceResponseDTO> priceList = prices.stream()
                .map(price -> new PriceResponseDTO(
                        price.getId(),
                        price.getAmount(),
                        price.getProduct().getId(),
                        price.getProduct().getName(),
                        price.getStore().getId(),
                        price.getStore().getName()
                ))
                .toList();

        Price cheapest = prices.stream()
                .min((p1, p2) ->
                        Double.compare(p1.getAmount(), p2.getAmount()))
                .orElseThrow();

        return new PriceComparisonDTO(
                product.getName(),
                priceList,
                cheapest.getStore().getName(),
                cheapest.getAmount()
        );
    }

    public PriceComparisonDTO comparePricesByName(String name) {

        Product product = productRepository.findByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        return comparePrices(product.getId());
    }

    public List<PriceResponseDTO> getAllPrices() {

        return priceRepository.findAll()
                .stream()
                .map(price -> new PriceResponseDTO(
                        price.getId(),
                        price.getAmount(),
                        price.getProduct().getId(),
                        price.getProduct().getName(),
                        price.getStore().getId(),
                        price.getStore().getName()
                ))
                .toList();
    }
    public void deletePrice(Long priceId){
        Price price = priceRepository.findById(priceId)
                .orElseThrow(()->
                        new PriceNotFoundException(
                                "Price not found"
                        ));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new InvalidCredentialsException(
                                "Shopkeeper not found"
                        ));

        LocalStore ownerStore = localStoreRepository.findByOwner(owner)
                .orElseThrow(()->
                        new StoreNotFoundException(
                                "Shopkeeper does not have a store"
                        ));

        if(!ownerStore.getId().equals(price.getStore().getId())){
            throw new AccessDeniedException(
                    "You can delete prices from your own store"
            );

        }
        priceRepository.delete(price);
    }
}
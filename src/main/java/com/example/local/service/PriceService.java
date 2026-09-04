package com.example.local.service;

import com.example.local.dto.PriceComparisonDTO;
import com.example.local.dto.PriceRequestDTO;
import com.example.local.dto.PriceResponseDTO;
import com.example.local.exception.NoPricesAvailableException;
import com.example.local.exception.ProductNotFoundException;
import com.example.local.exception.StoreNotFoundException;
import com.example.local.model.LocalStore;
import com.example.local.model.Price;
import com.example.local.model.Product;
import com.example.local.repository.LocalStoreRepository;
import com.example.local.repository.PriceRepository;
import com.example.local.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final LocalStoreRepository localStoreRepository;

    public PriceService(
            PriceRepository priceRepository,
            ProductRepository productRepository,
            LocalStoreRepository localStoreRepository) {

        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.localStoreRepository = localStoreRepository;
    }

    // Add a new price
    public Price addPrice(PriceRequestDTO request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        LocalStore store = localStoreRepository.findById(request.getStoreId())
                .orElseThrow(() ->
                        new StoreNotFoundException("Store not found"));

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

}
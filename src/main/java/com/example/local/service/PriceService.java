package com.example.local.service;

import com.example.local.dto.*;
import com.example.local.exception.*;
import com.example.local.model.LocalStore;
import com.example.local.model.Price;
import com.example.local.model.Product;
import com.example.local.model.User;
import com.example.local.repository.LocalStoreRepository;
import com.example.local.repository.PriceRepository;
import com.example.local.repository.ProductRepository;
import com.example.local.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {

    private static final double MAX_DISTANCE_KM = 5.0;

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final LocalStoreRepository localStoreRepository;
    private final UserRepository userRepository;

    public PriceService(
            PriceRepository priceRepository,
            ProductRepository productRepository,
            LocalStoreRepository localStoreRepository,
            UserRepository userRepository
    ) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.localStoreRepository = localStoreRepository;
        this.userRepository = userRepository;
    }

    // =========================
    // ADD PRICE
    // =========================

    public PriceResponseDTO addPrice(
            PriceRequestDTO request
    ) {

        LocalStore store = getCurrentStore();

        Product product =
                productRepository
                        .findByIdAndStoreId(
                                request.getProductId(),
                                store.getId()
                        )
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found in your store"
                                ));

        Price price =
                priceRepository
                        .findByProductIdAndStoreId(
                                product.getId(),
                                store.getId()
                        )
                        .orElse(null);

        if (price == null) {

            price = new Price();

            price.setProduct(product);
            price.setStore(store);
        }

        price.setAmount(request.getAmount());
        price.setAvailable(request.isAvailable());

        Price savedPrice =
                priceRepository.save(price);

        return convertToDTO(savedPrice);
    }

    // =========================
    // GET ALL PRICES
    // =========================

    public List<PriceResponseDTO> getAllPrices() {

        return priceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =========================
    // GET MY STORE PRICES
    // =========================

    public List<PriceResponseDTO> getMyStorePrices() {

        LocalStore store = getCurrentStore();

        return priceRepository
                .findByStoreId(store.getId())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =========================
    // NORMAL PRICE COMPARISON
    // =========================

    public PriceComparisonDTO comparePricesByName(
            String name
    ) {

        List<PriceResponseDTO> prices =
                priceRepository
                        .findByProduct_NameIgnoreCase(name)
                        .stream()
                        .map(this::convertToDTO)
                        .toList();

        if (prices.isEmpty()) {
            throw new NoPricesAvailableException(
                    "No prices available for this product"
            );
        }

        double cheapestPrice =
                prices.stream()
                        .filter(PriceResponseDTO::isAvailable)
                        .mapToDouble(PriceResponseDTO::getAmount)
                        .min()
                        .orElse(0);

        return new PriceComparisonDTO(
                name,
                cheapestPrice,
                prices
        );
    }

    // =========================
    // NEARBY PRICE COMPARISON
    // =========================

    public PriceComparisonDTO compareNearbyPrices(
            String name,
            double userLatitude,
            double userLongitude
    ) {

        List<Price> allPrices =
                priceRepository
                        .findByProduct_NameIgnoreCase(name);

        List<PriceResponseDTO> nearbyPrices =
                allPrices.stream()
                        .map(price -> {

                            double distance =
                                    calculateDistance(
                                            userLatitude,
                                            userLongitude,
                                            price.getStore().getLatitude(),
                                            price.getStore().getLongitude()
                                    );

                            return convertToDTO(
                                    price,
                                    distance
                            );
                        })
                        .filter(price ->
                                price.getDistanceKm()
                                        <= MAX_DISTANCE_KM
                        )
                        .sorted(
                                Comparator.comparingDouble(
                                        PriceResponseDTO::getDistanceKm
                                )
                        )
                        .toList();

        if (nearbyPrices.isEmpty()) {

            throw new NoPricesAvailableException(
                    "No shops selling this product were found within 5 km"
            );
        }

        double cheapestPrice =
                nearbyPrices.stream()
                        .filter(PriceResponseDTO::isAvailable)
                        .mapToDouble(PriceResponseDTO::getAmount)
                        .min()
                        .orElse(0);

        return new PriceComparisonDTO(
                name,
                cheapestPrice,
                nearbyPrices
        );
    }

    // =========================
    // UPDATE PRICE
    // =========================

    public PriceResponseDTO updatePrice(
            Long priceId,
            UpdatePriceRequestDTO request
    ) {

        LocalStore store = getCurrentStore();

        Price price =
                priceRepository
                        .findByIdAndStoreId(
                                priceId,
                                store.getId()
                        )
                        .orElseThrow(() ->
                                new PriceNotFoundException(
                                        "Price not found in your store"
                                ));

        price.setAmount(request.getAmount());
        price.setAvailable(request.isAvailable());

        return convertToDTO(
                priceRepository.save(price)
        );
    }

    // =========================
    // DELETE PRICE
    // =========================

    public void deletePrice(Long priceId) {

        LocalStore store = getCurrentStore();

        Price price =
                priceRepository
                        .findByIdAndStoreId(
                                priceId,
                                store.getId()
                        )
                        .orElseThrow(() ->
                                new PriceNotFoundException(
                                        "Price not found in your store"
                                ));

        priceRepository.delete(price);
    }

    // =========================
    // CURRENT USER
    // =========================

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Shopkeeper not found"
                        ));
    }

    // =========================
    // CURRENT STORE
    // =========================

    private LocalStore getCurrentStore() {

        User user = getCurrentUser();

        return localStoreRepository
                .findByOwner(user)
                .orElseThrow(() ->
                        new StoreNotFoundException(
                                "Shopkeeper does not have a store"
                        ));
    }

    // =========================
    // DTO CONVERSION
    // =========================

    private PriceResponseDTO convertToDTO(
            Price price
    ) {

        return convertToDTO(price, 0);
    }

    private PriceResponseDTO convertToDTO(
            Price price,
            double distanceKm
    ) {

        return new PriceResponseDTO(
                price.getId(),
                price.getAmount(),
                price.isAvailable(),
                price.getProduct().getId(),
                price.getProduct().getName(),
                price.getStore().getId(),
                price.getStore().getName(),
                price.getStore().getAddress(),
                price.getStore().getPhone(),
                Math.round(distanceKm * 100.0) / 100.0
        );
    }

    // =========================
    // HAVERSINE DISTANCE
    // =========================

    private double calculateDistance(
            double latitude1,
            double longitude1,
            double latitude2,
            double longitude2
    ) {

        final double EARTH_RADIUS_KM = 6371.0;

        double latDistance =
                Math.toRadians(latitude2 - latitude1);

        double lonDistance =
                Math.toRadians(longitude2 - longitude1);

        double a =
                Math.sin(latDistance / 2)
                        * Math.sin(latDistance / 2)
                        +
                        Math.cos(
                                Math.toRadians(latitude1)
                        )
                                *
                                Math.cos(
                                        Math.toRadians(latitude2)
                                )
                                *
                                Math.sin(lonDistance / 2)
                                *
                                Math.sin(lonDistance / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a)
                );

        return EARTH_RADIUS_KM * c;
    }
}
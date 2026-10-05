package com.example.local.repository;

import com.example.local.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStoreId(Long storeId);

    Optional<Product> findByIdAndStoreId(Long id, Long storeId);

    Optional<Product> findByNameIgnoreCaseAndStoreId(
            String name,
            Long storeId
    );

    List<Product> findByNameContainingIgnoreCase(String name);
}
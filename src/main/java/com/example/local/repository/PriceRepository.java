package com.example.local.repository;

import com.example.local.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByStoreId(Long storeId);

    Optional<Price> findByIdAndStoreId(Long id, Long storeId);

    Optional<Price> findByProductIdAndStoreId(
            Long productId,
            Long storeId
    );

    List<Price> findByProduct_NameIgnoreCase(String name);
}
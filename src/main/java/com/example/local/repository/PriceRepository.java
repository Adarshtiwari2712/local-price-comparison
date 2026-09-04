package com.example.local.repository;

import com.example.local.model.Price;
import com.example.local.model.Product;
import com.example.local.model.LocalStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository  extends JpaRepository<Price, Long>{
    Optional<Price> findByProductAndStore(Product product, LocalStore store);

    List<Price> findByProduct(Product product);
}

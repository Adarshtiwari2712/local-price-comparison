package com.example.local.repository;

import com.example.local.model.LocalStore;
import com.example.local.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalStoreRepository
        extends JpaRepository<LocalStore, Long> {

    Optional<LocalStore> findByOwner(User owner);
    boolean existsByOwner(User owner);
}
package com.example.local.repository;

import com.example.local.model.LocalStore;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocalStoreRepository extends JpaRepository<LocalStore, Long>{
}

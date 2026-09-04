package com.example.local.service;

import com.example.local.model.LocalStore;
import com.example.local.repository.LocalStoreRepository;
import org.springframework.stereotype.Service;
import com.example.local.dto.LocalStoreResponseDTO;
import java.util.List;

@Service
public class LocalStoreService {

    private final LocalStoreRepository localStoreRepository;

    public LocalStoreService(LocalStoreRepository localStoreRepository) {
        this.localStoreRepository = localStoreRepository;
    }

    public LocalStoreResponseDTO addStore(LocalStore store) {

        LocalStore savedStore = localStoreRepository.save(store);

        return new LocalStoreResponseDTO(
                savedStore.getId(),
                savedStore.getName(),
                savedStore.getAddress(),
                savedStore.getPhone()
        );
    }

    public List<LocalStoreResponseDTO> getAllStores() {

        return localStoreRepository.findAll()
                .stream()
                .map(store -> new LocalStoreResponseDTO(
                        store.getId(),
                        store.getName(),
                        store.getAddress(),
                        store.getPhone()
                ))
                .toList();
    }
}
package com.example.local.service;

import com.example.local.dto.LocalStoreRequestDTO;
import com.example.local.dto.LocalStoreResponseDTO;
import com.example.local.exception.InvalidCredentialsException;
import com.example.local.exception.StoreAlreadyExistsException;
import com.example.local.model.LocalStore;
import com.example.local.model.User;
import com.example.local.repository.LocalStoreRepository;
import com.example.local.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.local.exception.StoreHasPricesException;
import com.example.local.exception.StoreNotFoundException;
import com.example.local.model.Price;
import com.example.local.repository.PriceRepository;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
public class LocalStoreService {

    private final LocalStoreRepository localStoreRepository;
    private final UserRepository userRepository;
    private final PriceRepository priceRepository;

    public LocalStoreService(
            LocalStoreRepository localStoreRepository,
            UserRepository userRepository,
            PriceRepository priceRepository) {

        this.localStoreRepository = localStoreRepository;
        this.userRepository = userRepository;
        this.priceRepository = priceRepository;
    }

    public LocalStoreResponseDTO addStore(LocalStoreRequestDTO request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Shopkeeper not found"
                        ));
        if(localStoreRepository.findByOwner(owner).isPresent()){
            throw new StoreAlreadyExistsException(
                    "Shopkeeper already has a store"
            );
        }

        LocalStore store = new LocalStore(
                request.getName(),
                request.getAddress(),
                request.getPhone()
        );

        store.setOwner(owner);

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
    public void deleteStore(Long storeId) {

        LocalStore store = localStoreRepository.findById(storeId)
                .orElseThrow(() ->
                        new StoreNotFoundException("Store not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Shopkeeper not found"));

        if (store.getOwner() == null) {
            throw new AccessDeniedException(
                    "Store has no owner and cannot be deleted"
            );
        }

        if (!store.getOwner().getId().equals(owner.getId())) {
            throw new AccessDeniedException(
                    "You can only delete your own store"
            );
        }
        if (priceRepository.existsByStore(store)) {
            throw new StoreHasPricesException(
                    "Store cannot be deleted because prices exist for this store"
            );
        }

        localStoreRepository.delete(store);
    }
}
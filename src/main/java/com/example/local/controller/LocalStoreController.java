package com.example.local.controller;

import com.example.local.dto.LocalStoreRequestDTO;
import com.example.local.dto.LocalStoreResponseDTO;
import com.example.local.service.LocalStoreService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stores")
public class LocalStoreController {
    private final LocalStoreService localStoreService;

    public LocalStoreController(LocalStoreService localStoreService){
        this.localStoreService = localStoreService;

    }

    @PostMapping
    public LocalStoreResponseDTO addStore(@Valid @RequestBody LocalStoreRequestDTO request){
        return localStoreService.addStore(request);
    }

    @GetMapping
    public List<LocalStoreResponseDTO> getAllStores(){
        return localStoreService.getAllStores();
    }
    @DeleteMapping("/{id}")
    public String deleteStore(@PathVariable Long id) {
        localStoreService.deleteStore(id);
        return "Store deleted successfully";
    }

}


package tech.mlm.plutus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.SellerDTO;
import tech.mlm.plutus.dtos.requests.AddListOfSellersRequestDTO;
import tech.mlm.plutus.dtos.requests.AddSellerRequestDTO;
import tech.mlm.plutus.dtos.requests.CreateStoreDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.mappers.StoreMapper;
import tech.mlm.plutus.services.SellerService;
import tech.mlm.plutus.services.StoreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final SellerService sellerService;
    private final StoreMapper mapper;
    private final static String ROOT_URL = "/store";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ROOT_URL)
    public ResponseEntity<?> getStores(){
        return ResponseEntity.ok().body(mapper.toDTO(storeService.findAll()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ROOT_URL)
    public ResponseEntity<?> createStore(@RequestBody CreateStoreDTO request){
        return ResponseEntity.ok().body(storeService.createStore(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ROOT_URL + "/add-seller")
    public ResponseEntity<?> addSeller(@RequestBody AddSellerRequestDTO request) {
        return ResponseEntity.ok().body(storeService.addSeller(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ROOT_URL + "/add-list-of-seller")
    public ResponseEntity<?> addListOfSellers(@RequestBody AddListOfSellersRequestDTO request) {
        return ResponseEntity.ok().body(storeService.addListOfSellers(request));
    }

}

package tech.mlm.plutus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.SellerDTO;
import tech.mlm.plutus.dtos.requests.CreateSellerDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.mappers.SellerMapper;
import tech.mlm.plutus.services.SellerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    private final SellerMapper mapper;
    private final static String ROOT_URL = "/seller";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ROOT_URL)
    public ResponseEntity<List<SellerDTO>> getAllSellers(){
        return ResponseEntity.ok().body(sellerService.findAll());
    }

    public ResponseEntity<SellerDTO> setSellerStore(@PathVariable Long sellerId, @PathVariable Long storeId) {
        return ResponseEntity.ok(sellerService.setSellerStore(sellerId, storeId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ROOT_URL)
    public ResponseEntity<SellerDTO> createSeller(@RequestBody @Valid CreateSellerDTO sellerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.create(sellerDTO));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(ROOT_URL + "/{id}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(sellerService.findById(id));
    }
}

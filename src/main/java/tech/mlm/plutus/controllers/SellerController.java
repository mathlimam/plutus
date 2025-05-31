package tech.mlm.plutus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.requests.CreateSellerDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.mappers.SellerMapper;
import tech.mlm.plutus.services.SellerService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    private final SellerMapper sellerMapper;
    private final static String ROOT_URL = "/seller";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ROOT_URL)
    public ResponseEntity<?> getAllSellers(){
        return ResponseEntity.ok().body(sellerService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ROOT_URL)
    public ResponseEntity<?> createSeller(@RequestBody CreateSellerDTO sellerDTO) {
        SellerEntity seller = new SellerEntity(sellerDTO.name());
        return ResponseEntity.ok().body(sellerMapper.toDTO(sellerService.save(seller)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(ROOT_URL + "/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable Long id) {
        SellerEntity seller = sellerService.findById(id);
        return ResponseEntity.ok().body(sellerMapper.toDTO(seller));
    }
}

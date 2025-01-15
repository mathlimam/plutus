package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.mlm.plutus.dtos.SellerDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.services.SellerService;

import java.util.List;

@RestController
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/createseller")
    public ResponseEntity<?> createSeller(@RequestBody SellerDTO sellerDTO) {
        try {
            SellerEntity seller = new SellerEntity(sellerDTO.cpf(), sellerDTO.name());
            sellerService.save(seller);
            return ResponseEntity.ok(seller);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getsellers")
    public ResponseEntity<?> getSellers() {
        List<SellerEntity> sellers = sellerService.findAll();
        return ResponseEntity.ok(sellers);
    }
}

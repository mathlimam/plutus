package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.requests.CreateSellerDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.mappers.SellerMapper;
import tech.mlm.plutus.services.SellerService;



@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final SellerMapper sellerMapper;

    public SellerController(SellerService sellerService, SellerMapper sellerMapper) {
        this.sellerService = sellerService;
        this.sellerMapper = sellerMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSeller(@RequestBody CreateSellerDTO sellerDTO) {
        try {
            SellerEntity seller = new SellerEntity(sellerDTO.name());
            return ResponseEntity.ok().body(sellerMapper.toDTO(sellerService.save(seller)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable Long id) {
        try{
            SellerEntity seller = sellerService.findById(id);
            return ResponseEntity.ok().body(sellerMapper.toDTO(seller));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSellers(){
        return ResponseEntity.ok().body(sellerService.findAll());
    }
}

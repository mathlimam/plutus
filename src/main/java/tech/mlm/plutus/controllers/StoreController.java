package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.mlm.plutus.dtos.StoreDTO;
import tech.mlm.plutus.dtos.requests.AddSellerRequestDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.services.SellerService;
import tech.mlm.plutus.services.StoreService;

@RestController
public class StoreController {

    private final StoreService storeService;
    private final SellerService sellerService;

    public StoreController(StoreService storeService, SellerService sellerService) {
        this.storeService = storeService;
        this.sellerService = sellerService;
    }

    @PostMapping("/createstore")
    public ResponseEntity<?> createStore(@RequestBody StoreDTO storeDTO) {
        StoreEntity store = new StoreEntity(storeDTO.Name());
        return ResponseEntity.ok().body(storeService.save(store));
    }

    @PostMapping("/addseller")
    public ResponseEntity<?> addSeller(@RequestBody AddSellerRequestDTO request) {
        try {
            StoreEntity store = storeService.findById(request.storeId());
            SellerEntity seller = sellerService.findById(request.sellerId());

            store.addSeller(seller);
            return ResponseEntity.ok().body(storeService.save(store));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getStores(){
        return ResponseEntity.ok().body(storeService.findAll());
    }
}

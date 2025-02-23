package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.requests.AddSellerRequestDTO;
import tech.mlm.plutus.dtos.requests.CreateStoreDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.services.SellerService;
import tech.mlm.plutus.services.StoreService;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final SellerService sellerService;


    public StoreController(StoreService storeService, SellerService sellerService) {
        this.storeService = storeService;
        this.sellerService = sellerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStore(@RequestBody CreateStoreDTO request){
        try{
            return ResponseEntity.ok().body(storeService.createStore(request.name()));
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @GetMapping("")
    public ResponseEntity<?> getStores(){
        return ResponseEntity.ok().body(storeService.findAll());
    }
}

package tech.mlm.plutus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.ProductDTO;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.services.ProductService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final static String ROOT_URL = "/product";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ROOT_URL)
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ROOT_URL)
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO request){
        ProductEntity product = new ProductEntity(request.barcode(), request.name());
        return ResponseEntity.ok().body(productService.save(product));
    }
}

package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.ProductDTO;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO request){
        ProductEntity product = new ProductEntity(request.barcode(), request.name());
        productService.save(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.findAll());
    }
}

package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.mlm.plutus.dtos.ProductDTO;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.services.ProductService;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("createproduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO request){
        ProductEntity product = new ProductEntity(request.barcode(), request.name());
        productService.save(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.findAll());
    }
}

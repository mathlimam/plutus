package tech.mlm.plutus.services;

import org.springframework.stereotype.Service;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.repositories.ProductRepository;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public ProductEntity findByBarcode(String id) {
        return productRepository.findByBarcode(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

}

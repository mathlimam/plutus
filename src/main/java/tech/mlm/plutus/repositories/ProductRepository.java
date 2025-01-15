package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.mlm.plutus.entities.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByBarcode(String barcode);
}

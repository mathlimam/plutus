package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.mlm.plutus.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}

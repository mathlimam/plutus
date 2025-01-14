package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.mlm.plutus.entities.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
}

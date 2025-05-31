package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.StoreEntity;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    @Query("SELECT o FROM OperationEntity o WHERE :store IN (o.originStore, o.destinationStore)")
    public Optional<List<OperationEntity>> findAllByStore(StoreEntity storeEntity);
}

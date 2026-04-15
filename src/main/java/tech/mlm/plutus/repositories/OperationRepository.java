package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.utils.types.StatusType;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    @Query("SELECT o FROM OperationEntity o WHERE :store IN (o.originStore, o.destinationStore)")
    List<OperationEntity> findAllByStore(StoreEntity storeEntity);
    List<OperationEntity> findAllByOriginStore(StoreEntity store);
    List<OperationEntity> findAllByDestinationStore(StoreEntity store);
    List<OperationEntity> findAllByStoreAndStatus(StoreEntity store, StatusType status);

}

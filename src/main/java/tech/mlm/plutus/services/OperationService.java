package tech.mlm.plutus.services;

import org.springframework.stereotype.Service;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.repositories.OperationRepository;
import tech.mlm.plutus.types.OperationType;
import tech.mlm.plutus.types.StatusType;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    private final OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Optional<OperationEntity> createOperation(OperationType operationType,
                                                     ProductEntity productEntity,
                                                     int quantity,
                                                     StoreEntity originStore,
                                                     StoreEntity destinationStore,
                                                     SellerEntity originSeller,
                                                     SellerEntity destinationSeller) {

        OperationEntity operationEntity = new OperationEntity(operationType, productEntity, originStore, destinationStore, originSeller, destinationSeller, quantity);
        operationRepository.saveAndFlush(operationEntity);

        return Optional.of(operationEntity);
    }

    public Optional<OperationEntity> createOperation(OperationEntity operationEntity) {
        operationRepository.saveAndFlush(operationEntity);
        return Optional.of(operationEntity);
    }

    public Optional<OperationEntity> setOperationType(Long operationId, OperationType operationType) {
        OperationEntity operationEntity = getOperationById(operationId);
        operationEntity.setType(operationType);
        operationRepository.saveAndFlush(operationEntity);

        return Optional.of(operationEntity);
    }

    public Optional<OperationEntity> setOperationStatus(Long operationId, StatusType status) {
        OperationEntity operationEntity = getOperationById(operationId);
        operationEntity.setStatus(status);
        operationRepository.saveAndFlush(operationEntity);

        return Optional.of(operationEntity);
    }

    public OperationEntity getOperationById (Long operationId) {
        Optional<OperationEntity> operationEntityOptional = operationRepository.findById(operationId);
        return operationEntityOptional.orElseThrow(() -> new RuntimeException("Operation not found"));
    }

    public List<OperationEntity> getAllOperations() {
        return operationRepository.findAll();
    }
}

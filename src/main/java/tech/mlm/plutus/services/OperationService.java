package tech.mlm.plutus.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.OperationEntities;
import tech.mlm.plutus.dtos.requests.CreateOperationRequestDTO;
import tech.mlm.plutus.dtos.requests.UpdateOperationRequest;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.exceptions.InvalidRequestException;
import tech.mlm.plutus.exceptions.OperationNotFoundException;
import tech.mlm.plutus.repositories.OperationRepository;
import tech.mlm.plutus.utils.OperationValidator;
import tech.mlm.plutus.utils.types.OperationType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final ProductService productService;
    private final SellerService sellerService;
    private final StoreService storeService;
    private final OperationValidator operationValidator;

    public OperationEntity createOperation(OperationType operationType,
                                           ProductEntity productEntity,
                                           int quantity,
                                           StoreEntity originStore,
                                           StoreEntity destinationStore,
                                           SellerEntity originSeller,
                                           SellerEntity destinationSeller) {

        OperationEntity operationEntity = new OperationEntity(
                operationType,
                productEntity,
                originStore,
                destinationStore,
                originSeller,
                destinationSeller,
                quantity
        );

        return operationRepository.save(operationEntity);
    }

    public OperationEntity save(OperationEntity operationEntity) {
        return operationRepository.save(operationEntity);
    }

    @Transactional
    public OperationEntity createOperationEntity(CreateOperationRequestDTO dto) {

        operationValidator.validateEntities(dto);
        OperationEntities entities = loadEntities(dto);

        return new OperationEntity(
                dto.operationType(),
                entities.productEntity(),
                entities.originStore(),
                entities.destinationStore(),
                entities.originSeller(),
                entities.destinationSeller(),
                dto.quantity()
        );
    }

    private OperationEntities loadEntities(CreateOperationRequestDTO dto) {
        ProductEntity productEntity = productService.findByBarcode(dto.productId());
        StoreEntity originStore = storeService.findById(dto.originStoreId());
        StoreEntity destinationStore = storeService.findById(dto.destinationStoreId());
        SellerEntity originSeller = sellerService.findById(dto.originSellerId());
        SellerEntity destinationSeller = sellerService.findById(dto.destinationSellerId());

        return new OperationEntities(productEntity, originStore, destinationStore, originSeller, destinationSeller);
    }

    public OperationEntity updateOperation(UpdateOperationRequest request) {
        if (request == null) throw new InvalidRequestException("request cannot be null");

        OperationEntity entity = getOperationById(request.operationId());

        if (request.operationType() != null) entity.setOperationType(request.operationType());
        if (request.status() != null) entity.setOperationStatus(request.status());
        if (request.invoiceNumber() != null) entity.setOperationInvoice(request.invoiceNumber());
        if (request.quantity() != 0 && request.quantity() < entity.getQuantity()) entity.setQuantity(request.quantity());

        return operationRepository.save(entity);
    }

    public OperationEntity getOperationById(Long operationId) {
        return operationRepository.findById(operationId)
                .orElseThrow(() ->  new OperationNotFoundException("Operation with "+ operationId + " not found"));
    }

    public List<OperationEntity> getAllOperationsAsOriginStore(StoreEntity storeEntity) {
        List<OperationEntity> operationsList =  getAllOperationsByStore(storeEntity);
        return operationsList.stream()
                             .filter(operationEntity -> storeEntity.equals(operationEntity.getOriginStore()))
                             .toList();
    }

    public List<OperationEntity> getAllOperations(){
        List<OperationEntity> operationsList = operationRepository.findAll();
        return operationsList.isEmpty() ? List.of() : operationsList;
    }

    public List<OperationEntity> getAllOperationsAsDestinationStore(StoreEntity storeEntity){
        List<OperationEntity> operationsList = getAllOperationsByStore(storeEntity);
        return operationsList.stream()
                .filter(operationEntity -> storeEntity.equals(operationEntity.getDestinationStore()))
                .toList();
    }

    public List<OperationEntity> getAllOperationsByStore(StoreEntity storeEntity) {
        return operationRepository.findAllByStore(storeEntity).orElseThrow(() ->  new OperationNotFoundException("Operations  not found"));
    }
}

package tech.mlm.plutus.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.OperationEntities;
import tech.mlm.plutus.dtos.requests.UpdateOperationDTO;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.repositories.OperationRepository;
import tech.mlm.plutus.utils.OperationValidator;
import tech.mlm.plutus.utils.types.OperationType;
import tech.mlm.plutus.utils.types.StatusType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final ProductService productService;
    private final SellerService sellerService;
    private final StoreService storeService;
    private final OperationValidator operationValidator;

    public OperationService(OperationRepository operationRepository,
                            ProductService productService,
                            SellerService sellerService,
                            StoreService storeService,
                            OperationValidator operationValidator
    ) {
        this.operationRepository = operationRepository;
        this.productService = productService;
        this.sellerService = sellerService;
        this.storeService = storeService;
        this.operationValidator = operationValidator;
    }

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
    public OperationEntity createOperationEntity(OperationDTO dto) {
        operationValidator.validateEntities(dto);
        OperationEntities entities = loadEntities(dto);

        OperationEntity operationEntity = new OperationEntity(
                OperationType.valueOf(dto.operationType()),
                entities.productEntity(),
                entities.originStore(),
                entities.destinationStore(),
                entities.originSeller(),
                entities.destinationSeller(),
                dto.quantity()
        );

        return operationRepository.save(operationEntity);
    }

    private OperationEntities loadEntities(OperationDTO dto) {
        ProductEntity productEntity = productService.findByBarcode(dto.productBarcode());
        StoreEntity originStore = storeService.findById(dto.originStoreId());
        StoreEntity destinationStore = storeService.findById(dto.destinationStoreId());
        SellerEntity originSeller = sellerService.findById(dto.originSellerId());
        SellerEntity destinationSeller = sellerService.findById(dto.destinationSellerId());

        return new OperationEntities(productEntity, originStore, destinationStore, originSeller, destinationSeller);
    }

    public OperationEntity updateOperation(UpdateOperationDTO request) {
        if (request == null) throw new IllegalArgumentException("request cannot be null");

        OperationEntity operationEntity = operationRepository.findById(request.operationId()).orElseThrow(() -> new RuntimeException("Operation not found"));

        if (request.operationType() != null) setOperationType(operationEntity, OperationType.valueOf(request.operationType().toUpperCase()));
        if (request.status() != null) setOperationStatus(operationEntity, StatusType.valueOf(request.status().toUpperCase()));
        if (request.invoiceNumber() != null) setOperationInvoice(operationEntity, request.invoiceNumber());
        if (request.quantity() != 0) setQuantity(operationEntity, request.quantity());

        return operationEntity;
    }

    private void setOperationType(OperationEntity operationEntity, OperationType operationType) {
        operationRepository.save(operationEntity.setOperationType(operationType));
    }

    private void setOperationStatus(OperationEntity operationEntity, StatusType status) {
        operationRepository.save(operationEntity.setOperationStatus(status));
    }

    private void setOperationInvoice(OperationEntity operationEntity, String invoice){
        operationRepository.save(operationEntity.setOperationInvoice(invoice));
    }

    private void setQuantity(OperationEntity operationEntity, int quantity) {
        if(quantity > operationEntity.getQuantity()) throw new IllegalArgumentException("Quantity cannot be greater than operation quantity");
        operationRepository.save(operationEntity.setOperationQuantity(quantity));
    }

    public OperationEntity getOperationById(Long operationId) {
        return operationRepository.findById(operationId)
                .orElseThrow(() -> new RuntimeException("Operation not found"));
    }

    public List<OperationEntity> getAllOperations() {
        return operationRepository.findAll();
    }
}

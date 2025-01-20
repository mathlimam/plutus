package tech.mlm.plutus.services;

import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.requests.UpdateOperationDTO;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.repositories.OperationRepository;
import tech.mlm.plutus.types.OperationType;
import tech.mlm.plutus.types.StatusType;

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

    public OperationService(OperationRepository operationRepository, ProductService productService, SellerService sellerService, StoreService storeService) {
        this.operationRepository = operationRepository;
        this.productService = productService;
        this.sellerService = sellerService;
        this.storeService = storeService;
    }

    public OperationEntity createOperation(OperationType operationType,
                                                     ProductEntity productEntity,
                                                     int quantity,
                                                     StoreEntity originStore,
                                                     StoreEntity destinationStore,
                                                     SellerEntity originSeller,
                                                     SellerEntity destinationSeller) {

        OperationEntity operationEntity = new OperationEntity(operationType, productEntity, originStore, destinationStore, originSeller, destinationSeller, quantity);

        operationRepository.save(operationEntity);

        return operationEntity;
    }

    public Optional<OperationEntity> save(OperationEntity operationEntity) {
        operationRepository.save(operationEntity);
        return Optional.of(operationEntity);
    }

    public OperationEntity createOperationEntity(OperationDTO dto) {
        try {
            validateEntities(dto);
            OperationEntity entity = new OperationEntity(OperationType.valueOf(dto.operationType()),
                    productService.findByBarcode(dto.productBarcode()),
                    storeService.findById(dto.originStoreId()),
                    storeService.findById(dto.destinationStoreId()),
                    sellerService.findById(dto.originSellerId()),
                    sellerService.findById(dto.destinationSellerId()),
                    dto.quantity());
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void validateEntities(OperationDTO dto){
        validateOperationType(dto.operationType());
        validateSeller(dto.originSellerId());
        validateStore(dto.originStoreId());
        validateSellerStore(dto.originSellerId(), dto.originStoreId());

        validateSeller(dto.destinationSellerId());
        validateStore(dto.destinationStoreId());
        validateSellerStore(dto.destinationSellerId(), dto.destinationStoreId());

        validateProduct(dto.productBarcode());
        validateQuantity(dto.quantity());
    }

    private void validateSellerStore(Long sellerId, Long StoreId){
        if(storeService.findById(StoreId).getSellers().stream().noneMatch(seller -> seller.getId().equals(sellerId))){
            throw new IllegalArgumentException("Seller are not from the store");
        }
    }

    private void validateSeller(Long id){
        try{
            sellerService.findById(id);


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void validateStore(Long id){
        try {
            storeService.findById(id);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void validateProduct(String barcode){
        try {
            System.out.println(barcode);
            productService.findByBarcode(barcode);

        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void validateQuantity(int quantity){
        if(quantity <= 0) throw new IllegalArgumentException("Quantity cannot be less or equal to zero");
    }

    private void validateOperationType(String type){
        if(Arrays.stream(OperationType.values()).noneMatch(operationType -> operationType.name().equals(type))){
            throw new IllegalArgumentException("Operation type not supported");
        }
    }

    public OperationEntity updateOperation(UpdateOperationDTO request) {
        if (request == null) throw new IllegalArgumentException("request cannot be null");

        try {
            OperationEntity operationEntity = operationRepository.findById(request.operationId()).orElseThrow(() -> new RuntimeException("Operation not found"));

            if (request.operationType() != null) setOperationType(operationEntity, OperationType.valueOf(request.operationType().toUpperCase()));
            if (request.status() != null) setOperationStatus(operationEntity, StatusType.valueOf(request.status().toUpperCase()));
            if (request.invoiceNumber() != null) setOperationInvoice(operationEntity, request.invoiceNumber());
            if (request.quantity() != 0) setQuantity(operationEntity, request.quantity());

            return operationEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setOperationType(OperationEntity operationEntity, OperationType operationType) {
        operationEntity.setType(operationType);
        operationEntity.setUpdatedAt(LocalDateTime.now());
        operationRepository.save(operationEntity);
    }

    private void setOperationStatus(OperationEntity operationEntity, StatusType status) {
        operationEntity.setStatus(status);
        operationEntity.setUpdatedAt(LocalDateTime.now());
        operationRepository.save(operationEntity);
    }

    private void setOperationInvoice(OperationEntity operationEntity, String invoice){
        operationEntity.setInvoiceNumber(invoice);
        operationEntity.setUpdatedAt(LocalDateTime.now());
        operationRepository.save(operationEntity);
    }

    private void setQuantity(OperationEntity operationEntity, int quantity) {
        if(quantity > operationEntity.getQuantity()){ throw new IllegalArgumentException("Quantity cannot be greater than operation quantity"); }
        operationEntity.setQuantity(quantity);
        operationEntity.setUpdatedAt(LocalDateTime.now());
        operationRepository.save(operationEntity);
    }

    public OperationEntity getOperationById (Long operationId) {
        Optional<OperationEntity> operationEntityOptional = operationRepository.findById(operationId);
        return operationEntityOptional.orElseThrow(() -> new RuntimeException("Operation not found"));
    }

    public List<OperationEntity> getAllOperations() {
        return operationRepository.findAll();
    }
}

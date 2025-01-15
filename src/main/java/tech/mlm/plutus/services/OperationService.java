package tech.mlm.plutus.services;

import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.repositories.OperationRepository;
import tech.mlm.plutus.types.OperationType;
import tech.mlm.plutus.types.StatusType;

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

    public Optional<OperationEntity> save(OperationEntity operationEntity) {
        operationRepository.saveAndFlush(operationEntity);
        return Optional.of(operationEntity);
    }

    public OperationEntity createOperationEntity(OperationDTO dto) {
        try {
            validateEntities(dto);

            return new OperationEntity(OperationType.valueOf(dto.operationType()),
                                        productService.findByBarcode(dto.productBarcode()),
                                        storeService.findById(dto.originStoreId()),
                                        storeService.findById(dto.destinationStoreId()),
                                        sellerService.findByCpf(dto.originSellerCpf()),
                                        sellerService.findByCpf(dto.destinationSellerCpf()),
                                        dto.quantity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateEntities(OperationDTO dto){
        validateOperationType(dto.operationType());
        validateSeller(dto.originSellerCpf());
        validateSeller(dto.destinationSellerCpf());
        validateStore(dto.originStoreId());
        validateStore(dto.destinationStoreId());
        validateProduct(dto.productBarcode());
        validateQuantity(dto.quantity());
    }

    private void validateSeller(String cpf){
        try{
            sellerService.findByCpf(cpf);

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
            productService.findByBarcode(barcode);

        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void validateOperationType(String operationType){
        if(Arrays.stream(OperationType.values())
                 .noneMatch(type -> type.name().equals(operationType))) throw new IllegalArgumentException("Operation type not supported");
    }

    private void validateQuantity(int quantity){
        if(quantity <= 0) throw new IllegalArgumentException("Quantity cannot be less or equal to zero");
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

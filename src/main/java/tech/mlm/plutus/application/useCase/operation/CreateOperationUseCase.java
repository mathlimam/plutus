package tech.mlm.plutus.application.useCase.operation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.OperationEntities;
import tech.mlm.plutus.dtos.requests.CreateOperationRequestDTO;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.ProductEntity;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.mappers.OperationMapper;
import tech.mlm.plutus.repositories.OperationRepository;
import tech.mlm.plutus.services.OperationService;
import tech.mlm.plutus.services.ProductService;
import tech.mlm.plutus.services.SellerService;
import tech.mlm.plutus.services.StoreService;

@Component
@RequiredArgsConstructor
public class CreateOperationUseCase {
    private final OperationService service;
    private final OperationRepository operationRepository;
    private final OperationMapper mapper;
    private final ProductService productService;
    private final SellerService sellerService;
    private final StoreService storeService;


    @Transactional
    public OperationDTO execute(CreateOperationRequestDTO dto){
        var entities = loadEntities(dto);
        validateStoresRelationships(entities);

        var operation = new OperationEntity(
                dto.operationType(),
                entities.productEntity(),
                entities.originStore(),
                entities.destinationStore(),
                entities.originSeller(),
                entities.destinationSeller(),
                dto.quantity());

        return mapper.toDTO(operationRepository.save(operation));
    }

    private OperationEntities loadEntities(CreateOperationRequestDTO dto) {
        ProductEntity productEntity = productService.findByBarcode(dto.productId());
        StoreEntity originStore = storeService.findById(dto.originStoreId());
        StoreEntity destinationStore = storeService.findById(dto.destinationStoreId());
        SellerEntity originSeller = sellerService.findEntityById(dto.originSellerId());
        SellerEntity destinationSeller = sellerService.findEntityById(dto.destinationSellerId());


        return new OperationEntities(productEntity, originStore, destinationStore, originSeller, destinationSeller);
    }


    private void validateStoresRelationships(OperationEntities entities){
        validateRelationship(entities.originSeller(), entities.originStore());
        validateRelationship(entities.destinationSeller(), entities.destinationStore());
    }


    private void validateRelationship(SellerEntity seller, StoreEntity store){
        if(!seller.getStore().equals(store)){
            throw new IllegalArgumentException("Seller does not belong to the store");
        }
    }
}

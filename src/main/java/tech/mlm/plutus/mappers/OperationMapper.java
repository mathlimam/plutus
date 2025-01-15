package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.services.ProductService;
import tech.mlm.plutus.services.SellerService;
import tech.mlm.plutus.services.StoreService;
import tech.mlm.plutus.types.OperationType;
import tech.mlm.plutus.types.StatusType;

@Component
public class OperationMapper {
    private final StoreService storeService;
    private final SellerService sellerService;
    private final ProductService productService;

    public OperationMapper(StoreService storeService, SellerService sellerService, ProductService productService) {
        this.storeService = storeService;
        this.sellerService = sellerService;
        this.productService = productService;
    }

    public OperationEntity toEntity(OperationDTO dto){
        OperationEntity entity = new OperationEntity();
        entity.setType(OperationType.valueOf(dto.operationType()));
        entity.setCreatedAt(dto.createdAt());
        entity.setUpdatedAt(dto.updatedAt());
        entity.setStatus(StatusType.valueOf(dto.statusType()));
        entity.setOriginSeller(sellerService.findByCpf(dto.originSellerId()).get());
        entity.setDestinationSeller(sellerService.findByCpf(dto.destinationSellerId()).get());
        entity.setProductEntity(productService.findByBarcode(dto.productId()).get());
        entity.setQuantity(dto.quantity());
        entity.setOriginStore(storeService.findById(dto.originStoreId()).get());
        entity.setDestinationStore(storeService.findById(dto.destinationStoreId()).get());

        System.out.println(entity.toString());
        return entity;


    }
}

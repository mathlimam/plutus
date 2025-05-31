package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.requests.CreateOperationRequestDTO;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.services.OperationService;

import java.util.List;

@Component
public class OperationMapper {
    private final OperationService operationService;
    private final ProductMapper productMapper;
    private final SellerMapper sellerMapper;
    private final StoreMapper storeMapper;

    public OperationMapper(OperationService operationService, ProductMapper productMapper, SellerMapper sellerMapper, StoreMapper storeMapper) {
        this.operationService = operationService;
        this.productMapper = productMapper;
        this.sellerMapper = sellerMapper;
        this.storeMapper = storeMapper;
    }

    /*public OperationEntity toEntity(OperationDTO dto){
        try {
            if (dto == null) throw new IllegalArgumentException("dto cannot be null");
            return operationService.createOperationEntity(dto);

        } catch (Exception e){ throw new IllegalArgumentException("dto cannot be convert to OperationDTO", e);}
    }*/



    public OperationDTO toDTO(OperationEntity entity){
        return new OperationDTO(entity.getType(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getStatus(),
                sellerMapper.toDTO(entity.getOriginSeller()),
                sellerMapper.toDTO(entity.getDestinationSeller()),
                storeMapper.toDTO(entity.getOriginStore()),
                storeMapper.toDTO(entity.getDestinationStore()),
                productMapper.toDTO(entity.getProductEntity()),
                entity.getQuantity());
    }

    public List<OperationDTO> toDTO(List<OperationEntity> operations){
        return operations.stream().map(this::toDTO).toList();
    }
}

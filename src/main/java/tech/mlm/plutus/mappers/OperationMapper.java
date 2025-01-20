package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.services.OperationService;


import java.util.Optional;

@Component
public class OperationMapper {
    private final OperationService operationService;

    public OperationMapper(OperationService operationService) {
        this.operationService = operationService;
    }

    public Optional<OperationEntity> toEntity(OperationDTO dto){

        try {
            if (dto == null) throw new IllegalArgumentException("dto cannot be null");
            return Optional.of(operationService.createOperationEntity(dto));

        } catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    public OperationDTO toDto(OperationEntity entity){
        return new OperationDTO(entity.getType().name(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getStatus().name(),
                entity.getOriginSeller().getId(),
                entity.getDestinationSeller().getId(),
                entity.getOriginStore().getId(),
                entity.getDestinationStore().getId(),
                entity.getProductEntity().getBarcode(),
                entity.getQuantity());
    }
}

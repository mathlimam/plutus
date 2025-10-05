package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.SellerDTO;
import tech.mlm.plutus.dtos.StoreDTO;
import tech.mlm.plutus.dtos.responses.GetStoreResponseDTO;
import tech.mlm.plutus.entities.StoreEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StoreMapper {

    public StoreDTO toDTO(StoreEntity entity) {
        if(entity == null) return null;
        return new StoreDTO(entity.getId(), entity.getName());
    }

    public List<StoreDTO> toDTO(List<StoreEntity> entities) {
        if(entities == null) return null;
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public GetStoreResponseDTO toGetStoreResponseDTO(StoreEntity entity) {
        if(entity == null) return null;
        return new GetStoreResponseDTO(entity.getId(),
                                       entity.getName(),
                                       entity.getSellers().stream()
                                              .map(seller -> new SellerDTO(seller.getId(),
                                                      seller.getName(),
                                                      entity.getName()))
                                              .toList());
    }
}

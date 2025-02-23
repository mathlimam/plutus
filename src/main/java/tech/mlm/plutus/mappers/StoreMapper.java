package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.StoreDTO;
import tech.mlm.plutus.entities.StoreEntity;

@Component
public class StoreMapper {

    public StoreDTO toDTO(StoreEntity entity) {
        return new StoreDTO(entity.getId(), entity.getName());
    }
}

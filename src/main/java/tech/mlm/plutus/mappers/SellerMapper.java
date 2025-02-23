package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.SellerDTO;
import tech.mlm.plutus.entities.SellerEntity;

@Component
public class SellerMapper {

    public SellerDTO toDTO(SellerEntity entity) {
        return new SellerDTO(entity.getId(), entity.getName());
    }
}

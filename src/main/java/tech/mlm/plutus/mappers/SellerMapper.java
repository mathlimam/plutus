package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.SellerDTO;
import tech.mlm.plutus.entities.SellerEntity;
import java.util.List;

@Component
public class SellerMapper {

    public SellerDTO toDTO(SellerEntity entity) {
        var store = entity.getStore() == null ? null : entity.getStore().getName();
        return new SellerDTO(entity.getId(), entity.getName(), store);
    }

    public List<SellerDTO> toDTO(List<SellerEntity> entities) {
        return entities.stream().map(this::toDTO).toList();
    }
}

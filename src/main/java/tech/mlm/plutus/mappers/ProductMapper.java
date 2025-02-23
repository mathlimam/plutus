package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.ProductDTO;
import tech.mlm.plutus.entities.ProductEntity;

@Component
public class ProductMapper {

    public ProductDTO toDTO(ProductEntity entity) {
        return new ProductDTO(entity.getBarcode(), entity.getName());
    }
}

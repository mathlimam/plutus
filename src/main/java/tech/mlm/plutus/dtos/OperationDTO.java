package tech.mlm.plutus.dtos;

import tech.mlm.plutus.utils.types.OperationType;
import tech.mlm.plutus.utils.types.StatusType;

import java.time.LocalDateTime;

public record OperationDTO (OperationType operationType,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            StatusType statusType,
                            SellerDTO originSellerDTO,
                            SellerDTO destinationSellerDTO,
                            StoreDTO originStoreDTO,
                            StoreDTO destinationStoreDTO,
                            ProductDTO productDTO,
                            int quantity) {
}

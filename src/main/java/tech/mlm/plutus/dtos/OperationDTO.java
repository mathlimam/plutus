package tech.mlm.plutus.dtos;

import tech.mlm.plutus.types.OperationType;
import tech.mlm.plutus.types.StatusType;

import java.time.LocalDateTime;

public record OperationDTO (Long id,
                            OperationType operationType,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            StatusType statusType,
                            SellerDTO originSeller,
                            SellerDTO destinationSeller,
                            StoreDTO originStore,
                            StoreDTO destinationStore,
                            ProductDTO productDTO,
                            int quatity) {
}

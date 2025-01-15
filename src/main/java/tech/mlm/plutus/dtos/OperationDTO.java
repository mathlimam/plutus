package tech.mlm.plutus.dtos;

import java.time.LocalDateTime;

public record OperationDTO (String operationType,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            String statusType,
                            String originSellerId,
                            String destinationSellerId,
                            Long originStoreId,
                            Long destinationStoreId,
                            String productId,
                            int quantity) {
}

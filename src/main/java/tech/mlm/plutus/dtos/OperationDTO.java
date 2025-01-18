package tech.mlm.plutus.dtos;

import java.time.LocalDateTime;

public record OperationDTO (String operationType,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            String statusType,
                            Long originSellerId,
                            Long destinationSellerId,
                            Long originStoreId,
                            Long destinationStoreId,
                            String productBarcode,
                            int quantity) {
}

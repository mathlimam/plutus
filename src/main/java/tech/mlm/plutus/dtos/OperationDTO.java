package tech.mlm.plutus.dtos;

import java.time.LocalDateTime;

public record OperationDTO (String operationType,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt,
                            String statusType,
                            String originSellerCpf,
                            String destinationSellerCpf,
                            Long originStoreId,
                            Long destinationStoreId,
                            String productBarcode,
                            int quantity) {
}

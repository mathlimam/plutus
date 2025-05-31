package tech.mlm.plutus.dtos.requests;

import tech.mlm.plutus.utils.types.OperationType;

public record CreateOperationRequestDTO(
        OperationType operationType,
        Long originSellerId,
        Long destinationSellerId,
        Long originStoreId,
        Long destinationStoreId,
        String productId,
        int quantity
) {}
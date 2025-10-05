package tech.mlm.plutus.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tech.mlm.plutus.utils.types.OperationType;

public record CreateOperationRequestDTO(


        @NotNull OperationType operationType,
        @NotNull Long originSellerId,
        @NotNull Long destinationSellerId,
        @NotNull Long originStoreId,
        @NotNull Long destinationStoreId,
        @NotNull @NotBlank String productId,
        @NotNull @Positive int quantity
) {}
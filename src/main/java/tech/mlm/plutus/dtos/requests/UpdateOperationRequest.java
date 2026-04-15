package tech.mlm.plutus.dtos.requests;

import jakarta.validation.constraints.NotNull;
import tech.mlm.plutus.utils.types.OperationType;
import tech.mlm.plutus.utils.types.StatusType;

public record UpdateOperationRequest(
        @NotNull Long operationId, OperationType operationType, StatusType status, String invoiceNumber, int quantity) {}

package tech.mlm.plutus.dtos.requests;

import tech.mlm.plutus.utils.types.OperationType;
import tech.mlm.plutus.utils.types.StatusType;

public record UpdateOperationRequest(Long operationId, OperationType operationType, StatusType status, String invoiceNumber, int quantity) {}

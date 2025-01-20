package tech.mlm.plutus.dtos.requests;

public record UpdateOperationDTO(Long operationId, String operationType, String status, String invoiceNumber, int quantity) {}

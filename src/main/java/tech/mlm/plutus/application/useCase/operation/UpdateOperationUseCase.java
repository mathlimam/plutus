package tech.mlm.plutus.application.useCase.operation;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.requests.UpdateOperationRequest;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.exceptions.OperationNotFoundException;
import tech.mlm.plutus.mappers.OperationMapper;
import tech.mlm.plutus.repositories.OperationRepository;

@Component
@RequiredArgsConstructor
public class UpdateOperationUseCase {
    private final OperationRepository operationRepository;
    private final OperationMapper mapper;

    public OperationDTO execute(@NotNull UpdateOperationRequest request){
        var operation = findById(request.operationId());
        operation.update(request);
        return mapper.toDTO(operationRepository.save(operation));
    }

    private OperationEntity findById(Long id){
        return operationRepository.findById(id).orElseThrow(() -> new OperationNotFoundException("Operation not found"));
    }
}

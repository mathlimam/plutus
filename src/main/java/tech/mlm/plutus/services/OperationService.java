package tech.mlm.plutus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.exceptions.OperationNotFoundException;
import tech.mlm.plutus.mappers.OperationMapper;
import tech.mlm.plutus.repositories.OperationRepository;
import tech.mlm.plutus.utils.types.StatusType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper mapper;


    public OperationDTO getById(Long operationId) {
        return mapper.toDTO(operationRepository.findById(operationId)
                .orElseThrow(() ->  new OperationNotFoundException("Operation with "+ operationId + " not found")));
    }

    public List<OperationDTO> getAll() {
        return mapper.toDTO(operationRepository.findAll());
    }

    @EntityGraph
    public List<OperationDTO> getAllOperationsAsOriginStore(StoreEntity storeEntity) {
        return mapper.toDTO(operationRepository.findAllByOriginStore(storeEntity));
    }

    @EntityGraph
    public List<OperationDTO> getAllOperationsAsDestinationStore(StoreEntity storeEntity) {
        return mapper.toDTO(operationRepository.findAllByDestinationStore(storeEntity));
    }

    public List<OperationDTO> getAllOpenOperationsByStore(StoreEntity storeEntity) {
        return mapper.toDTO(operationRepository.findAllByStoreAndStatus(storeEntity, StatusType.PENDING));
    }

    public List<OperationDTO> getAllOperationsByStore(StoreEntity storeEntity) {
        return mapper.toDTO(operationRepository.findAllByStore(storeEntity));
    }
}

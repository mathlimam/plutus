package tech.mlm.plutus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.entities.OperationEntity;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface OperationMapper {
    OperationDTO toDTO(OperationEntity operation);
    OperationEntity toEntity(OperationDTO dto);
}

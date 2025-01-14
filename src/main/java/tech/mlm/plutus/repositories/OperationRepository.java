package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.mlm.plutus.entities.OperationEntity;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {}

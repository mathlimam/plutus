package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.mlm.plutus.entities.SellerEntity;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {}

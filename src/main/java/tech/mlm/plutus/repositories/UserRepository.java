package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.lastshield.security.core.annotations.UserHandler;
import tech.mlm.plutus.entities.UserEntity;

import java.util.Optional;

@UserHandler
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsername(String username);
    boolean existsByUsername(String username);
}

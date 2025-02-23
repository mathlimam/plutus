package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.lastbox.jwt.TokenEntity;
import tech.lastbox.jwt.TokenStore;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, String>, TokenStore {
    @SuppressWarnings("unchecked")
    TokenEntity save(TokenEntity token);
    Optional<TokenEntity> findById(String id);
}

package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.mlm.plutus.entities.StoreEntity;




public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END " +
                    "FROM store_entity s " +
                    "JOIN store_entity_sellers ss ON s.id = ss.store_entity_id " +
                    "WHERE ss.sellers_id = :sellerId AND s.id = :storeId",
            nativeQuery = true)
    boolean existsBySellerAndStoreId(@Param("storeId") Long storeId, @Param("sellerId") Long sellerId);
}

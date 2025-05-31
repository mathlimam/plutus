package tech.mlm.plutus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.mlm.plutus.entities.StoreEntity;




public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    String EXISTS_BY_SELLER_AND_STORE_ID_QUERY =  "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
                                            "FROM SellerEntity s " +
                                            "WHERE s.id = :sellerId AND s.store.id = :storeId";
    @Query(EXISTS_BY_SELLER_AND_STORE_ID_QUERY)
    boolean existsBySellerAndStoreId(@Param("sellerId") Long sellerId, @Param("storeId") Long storeId);
}

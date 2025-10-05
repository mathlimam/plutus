package tech.mlm.plutus.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.requests.CreateOperationRequestDTO;
import tech.mlm.plutus.exceptions.EntityNotFoundException;
import tech.mlm.plutus.repositories.ProductRepository;
import tech.mlm.plutus.repositories.SellerRepository;
import tech.mlm.plutus.repositories.StoreRepository;
import tech.mlm.plutus.utils.types.LabelsEnum;
import tech.mlm.plutus.utils.types.OperationType;

import java.util.Arrays;
import java.util.Objects;

import static tech.mlm.plutus.utils.types.LabelsEnum.SELLER;
import static tech.mlm.plutus.utils.types.LabelsEnum.STORE;

@Component
@RequiredArgsConstructor
public class OperationValidator {
    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    public void validateEntities(CreateOperationRequestDTO dto){
        Objects.requireNonNull(dto, "CreateOperationRequestDTO cannot be null");
        validateOperationType(dto.operationType().name());
        validateStores(dto.originStoreId(), dto.destinationStoreId());
        validateSellers(dto.originSellerId(), dto.destinationSellerId(), dto.originStoreId(), dto.destinationStoreId());
        validateProduct(dto.productId());
    }

    private void validateProduct(String barcode) {
        productRepository.findByBarcode(barcode).orElseThrow(() -> new EntityNotFoundException("Product with barcode " + barcode + " cannot be found"));
    }

    private void validateOperationType(String type) {
        if (Arrays.stream(OperationType.values()).noneMatch(operationType -> operationType.name().equals(type))) {
            throw new IllegalArgumentException("Operation type not supported");
        }
    }

    private void validateStores(Long originStoreId, Long destinationStoreId) {
        if (Objects.equals(originStoreId, destinationStoreId)) {
            throw new IllegalArgumentException("Origin and destination stores must be different");
        }
        validateEntity(originStoreId, storeRepository, STORE);
        validateEntity(destinationStoreId, storeRepository, STORE);
    }

    private void validateSellers(Long originSellerId, Long destinationSellerId, Long originStoreId, Long destinationStoreId) {
        if (Objects.equals(originSellerId, destinationSellerId)) {
            throw new IllegalArgumentException("Origin and destination sellers must be different");
        }
        validateEntity(originSellerId, sellerRepository, SELLER);
        validateEntity(destinationSellerId, sellerRepository, SELLER);

        validateSellerStore(originSellerId, originStoreId);
        validateSellerStore(destinationSellerId, destinationStoreId);
    }

    private static <R extends JpaRepository<?, Long>> void validateEntity(Long id, R repo, LabelsEnum label) {
        requireExists(repo.existsById(id), label + "entity with id " + id + " cannot be found.");
    }

    private static void requireExists(boolean entityExists, String messageIfNotExists){
        if (!entityExists) {
            throw new EntityNotFoundException(messageIfNotExists);
        }
    }

    private void validateSellerStore(Long sellerId, Long storeId) {
        if (!storeRepository.existsBySellerAndStoreId(sellerId, storeId)) {
            throw new IllegalArgumentException("Seller does not belong to the store");
        }
    }
}

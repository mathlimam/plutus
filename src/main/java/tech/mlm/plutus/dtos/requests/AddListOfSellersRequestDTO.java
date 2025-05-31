package tech.mlm.plutus.dtos.requests;

import java.util.List;

public record AddListOfSellersRequestDTO(Long storeId, List<Long> sellerIds) {
}

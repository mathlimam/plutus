package tech.mlm.plutus.dtos.responses;

import tech.mlm.plutus.dtos.SellerDTO;

import java.util.List;

public record GetStoreResponseDTO (Long id, String name, List<SellerDTO> sellers){
}

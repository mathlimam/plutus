package tech.mlm.plutus.dtos;

import java.util.List;

public record StoreDTO(Long id, String Name, List<SellerDTO> sellerDTOList) {}

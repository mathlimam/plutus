package tech.mlm.plutus.dtos.requests;

import tech.mlm.plutus.utils.types.Role;

public record UserRegistrationDTO(String username, String password, Long storeId, Role role) {}

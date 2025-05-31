package tech.mlm.plutus.dtos.requests;

import tech.mlm.plutus.utils.types.Roles;

public record UserRegistrationDTO(String username, String password, String role) {}

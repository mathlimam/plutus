package tech.mlm.plutus.dtos.responses;

import tech.lastbox.jwt.Token;

public record LoginResponse(String username, Token token) {
}

package tech.mlm.plutus.dtos.responses;

import tech.lastbox.jwt.Token;
import tech.mlm.plutus.dtos.UserDTO;

public record UserRegistrationResponse(UserDTO userDTO, Token token) {

}

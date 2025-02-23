package tech.mlm.plutus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.lastbox.jwt.JwtService;
import tech.lastbox.jwt.Token;
import tech.mlm.plutus.configuration.environment.JwtProperties;
import tech.mlm.plutus.dtos.UserDTO;
import tech.mlm.plutus.dtos.requests.UserLoginDTO;
import tech.mlm.plutus.dtos.requests.UserRegistrationDTO;
import tech.mlm.plutus.dtos.responses.LoginResponse;
import tech.mlm.plutus.dtos.responses.UserRegistrationResponse;
import tech.mlm.plutus.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;


    public AuthController(UserService userService, JwtProperties jwtProperties, JwtService jwtService){
        this.userService = userService;
        this.jwtProperties = jwtProperties;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO dto){
        try{
            UserDTO userDTO = userService.createUser(dto);
            Token token = jwtService.generateToken(userDTO.username(), jwtProperties.getIssuer());
            return ResponseEntity.ok().body(new UserRegistrationResponse(userDTO, token));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO dto){
        boolean authentication = userService.authenticateUser(dto.username(), dto.password());
        if (authentication) {
            Token token = jwtService.generateToken(dto.username(), jwtProperties.getIssuer());
            return ResponseEntity.ok().body(new LoginResponse(dto.username(), token));
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

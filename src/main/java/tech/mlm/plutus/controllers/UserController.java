package tech.mlm.plutus.controllers;import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.entities.UserEntity;
import tech.mlm.plutus.mappers.OperationMapper;
import tech.mlm.plutus.mappers.UserMapper;
import tech.mlm.plutus.services.OperationService;
import tech.mlm.plutus.services.StoreService;
import tech.mlm.plutus.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final static String ROOT_URL = "/user";
    private final UserMapper userMapper;
    private final UserService userService;
    private final StoreService storeService;
    private final OperationService operationService;
    private final OperationMapper operationMapper;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(ROOT_URL + "/")
    public ResponseEntity<?> getUserDetails() {
        UserEntity authenticatedUser = getUserBySecurityContext();
        return ResponseEntity.ok().body(userMapper.toDTO(authenticatedUser));
    }

    private static UserEntity getUserBySecurityContext(){
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(ROOT_URL + "/operation")
    public ResponseEntity<?> getAllOperations(){
        UserEntity user = getUserBySecurityContext();
        List<OperationEntity> operations = operationService.getAllOperationsByStore(user.getStore());
        return ResponseEntity.ok().body(operationMapper.toDTO(operations));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(ROOT_URL + "/set-user-store/{userId}&{storeId}")
    public ResponseEntity<?> setUserStore(@PathVariable Long userId, @PathVariable Long storeId) {
        UserEntity user = userService.findById(userId);
        user.setStore(storeService.findById(storeId));
        return ResponseEntity.ok().body(userMapper.toDTO(userService.save(user)));
    }
}

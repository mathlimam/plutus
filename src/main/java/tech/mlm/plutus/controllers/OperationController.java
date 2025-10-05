package tech.mlm.plutus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.requests.CreateOperationRequestDTO;
import tech.mlm.plutus.dtos.requests.UpdateOperationRequest;

import tech.mlm.plutus.services.OperationService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;
    private final static String ROOT_URL = "/operation";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ROOT_URL)
    public ResponseEntity<?> getAllOperations() {
        return ResponseEntity.ok().body(operationService.getAllOperations());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(ROOT_URL )
    public ResponseEntity<?> createOperation(@RequestBody CreateOperationRequestDTO operationDTO) {
        return ResponseEntity.ok().body(operationService.createOperationEntity(operationDTO));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(ROOT_URL)
    public ResponseEntity<?> updateOperation(@RequestBody UpdateOperationRequest request) {
        return ResponseEntity.ok().body(operationService.updateOperation(request));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(ROOT_URL + "/{id}")
    public ResponseEntity<?> getOperationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(operationService.getOperationById(id));
    }

}

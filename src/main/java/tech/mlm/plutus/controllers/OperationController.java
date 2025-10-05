package tech.mlm.plutus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.requests.CreateOperationRequestDTO;
import tech.mlm.plutus.dtos.requests.UpdateOperationRequest;

import tech.mlm.plutus.services.OperationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;
    private final static String ROOT_URL = "/operation";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ROOT_URL)
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        return ResponseEntity.ok().body(operationService.getAllOperations());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(ROOT_URL )
    public ResponseEntity<OperationDTO> createOperation(@RequestBody @Valid CreateOperationRequestDTO operationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operationService.createOperationEntity(operationDTO));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(ROOT_URL)
    public ResponseEntity<OperationDTO> updateOperation(@RequestBody @Valid UpdateOperationRequest request) {
        return ResponseEntity.ok().body(operationService.updateOperation(request));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(ROOT_URL + "/{id}")
    public ResponseEntity<OperationDTO> getOperationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(operationService.getOperationById(id));
    }
}

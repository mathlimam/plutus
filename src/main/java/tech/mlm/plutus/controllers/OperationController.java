package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.requests.UpdateOperationDTO;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.mappers.OperationMapper;
import tech.mlm.plutus.services.OperationService;

@RestController
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/createoperation")
    public ResponseEntity<?> createOperation(@RequestBody OperationDTO operationDTO) {
        try {
            OperationEntity entity = operationService.createOperationEntity(operationDTO);
            return ResponseEntity.ok().body(operationService.save(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/operations")
    public ResponseEntity<?> getOperations() {
        try{
            return ResponseEntity.ok().body(operationService.getAllOperations());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/operations/{id}")
    public ResponseEntity<?> getOperationById(@PathVariable("id") Long id) {
        try{
            return ResponseEntity.ok().body(operationService.getOperationById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/operation")
    public ResponseEntity<?> updateOperation(@RequestBody UpdateOperationDTO request) {
        try{
            return ResponseEntity.ok().body(operationService.updateOperation(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

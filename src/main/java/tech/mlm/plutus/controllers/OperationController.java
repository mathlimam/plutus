package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.dtos.requests.UpdateOperationRequest;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.services.OperationService;

@RestController
@RequestMapping("/operations")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOperation(@RequestBody OperationDTO operationDTO) {
        try {
            OperationEntity entity = operationService.createOperationEntity(operationDTO);
            return ResponseEntity.ok().body(operationService.save(entity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getOperations() {
        try{
            return ResponseEntity.ok().body(operationService.getAllOperations());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOperationById(@PathVariable("id") Long id) {
        try{
            return ResponseEntity.ok().body(operationService.getOperationById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOperation(@RequestBody UpdateOperationRequest request) {
        try{
            return ResponseEntity.ok().body(operationService.updateOperation(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

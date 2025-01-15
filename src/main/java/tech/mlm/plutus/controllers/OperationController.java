package tech.mlm.plutus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.mlm.plutus.dtos.OperationDTO;
import tech.mlm.plutus.entities.OperationEntity;
import tech.mlm.plutus.mappers.OperationMapper;
import tech.mlm.plutus.services.OperationService;

@RestController
public class OperationController {

    private final OperationService operationService;
    private final OperationMapper operationMapper;

    public OperationController(OperationService operationService, OperationMapper operationMapper) {
        this.operationService = operationService;
        this.operationMapper = operationMapper;

    }

    @PostMapping("/createoperation")
    public ResponseEntity<?> createOperation(@RequestBody OperationDTO operationDTO) {
        try {
            System.out.println(operationDTO.toString());
            OperationEntity operationEntity = operationMapper.toEntity(operationDTO);
            System.out.println(operationEntity.toString());
            operationService.createOperation(operationEntity);
            return ResponseEntity.ok().body(operationEntity);
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
}

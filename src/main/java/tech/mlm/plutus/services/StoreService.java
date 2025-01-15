package tech.mlm.plutus.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.repositories.StoreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository repository;

    public StoreService(StoreRepository repository) {
        this.repository = repository;
    }

    public StoreEntity save(StoreEntity store) {
        return repository.save(store);
    }

    public StoreEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Store with id " + id + " not found"));
    }

    public List<StoreEntity> findAll() {
        return repository.findAll();
    }
}

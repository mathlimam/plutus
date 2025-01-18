package tech.mlm.plutus.services;

import org.springframework.stereotype.Service;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.repositories.SellerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    private final SellerRepository repository;

    public SellerService(SellerRepository repository) {
        this.repository = repository;
    }

    public SellerEntity save(SellerEntity entity) {
        return repository.save(entity);
    }

    public List<SellerEntity> findAll() {
        return repository.findAll();
    }

    public SellerEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Seller with id " + id + " not found"));
    }
}

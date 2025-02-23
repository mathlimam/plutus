package tech.mlm.plutus.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.StoreDTO;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.exceptions.StoreNotFoundException;
import tech.mlm.plutus.mappers.StoreMapper;
import tech.mlm.plutus.repositories.StoreRepository;

import java.util.List;


@Service
public class StoreService {

    private final StoreRepository repository;
    private final StoreMapper storeMapper;


    public StoreService(StoreRepository repository, StoreMapper storeMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.storeMapper = storeMapper;
    }

    public boolean existsSellerByIdAndStoreId(Long storeId, Long sellerId) {
        return repository.existsBySellerAndStoreId(storeId, sellerId);
    }

    public StoreEntity save(StoreEntity store) {
        return repository.save(store);
    }

    public StoreDTO createStore(String name){
        StoreEntity store = new StoreEntity(name);
        return storeMapper.toDTO(repository.save(store));
    }

    public StoreEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new StoreNotFoundException("Store with id " + id + " not found"));
    }

    public List<StoreEntity> findAll() {
        return repository.findAll();
    }
}

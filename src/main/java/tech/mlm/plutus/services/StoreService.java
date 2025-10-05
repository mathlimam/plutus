package tech.mlm.plutus.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.StoreDTO;
import tech.mlm.plutus.dtos.requests.AddListOfSellersRequestDTO;
import tech.mlm.plutus.dtos.requests.AddSellerRequestDTO;
import tech.mlm.plutus.dtos.requests.CreateStoreDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.exceptions.StoreNotFoundException;
import tech.mlm.plutus.mappers.StoreMapper;
import tech.mlm.plutus.repositories.SellerRepository;
import tech.mlm.plutus.repositories.StoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository repository;
    private final StoreMapper storeMapper;
    private final SellerRepository sellerRepository;

    @Transactional
    public StoreEntity save(StoreEntity store) {
        return repository.save(store);
    }

    @Transactional
    public List<StoreEntity> save(List<StoreEntity> stores) {
        return repository.saveAll(stores);
    }

    @Transactional
    public StoreDTO createStore(CreateStoreDTO request) {
        StoreEntity store = new StoreEntity();
        store.setName(request.name());
        return storeMapper.toDTO(repository.save(store));
    }

    @Transactional
    public StoreDTO addSeller(AddSellerRequestDTO request) {
        StoreEntity store = findById(request.storeId());
        SellerEntity seller = sellerRepository.findById(request.sellerId()).orElseThrow();
        store.addSeller(seller);
        seller.setStore(store);
        return storeMapper.toDTO(repository.save(store));
    }

    @Transactional
    public List<StoreDTO> addListOfSellers(AddListOfSellersRequestDTO request) {
        List<Long> sellerIds = request.sellerIds();
        var store = findById(request.storeId());
        return storeMapper.toDTO(sellerIds.stream().map(sellerId -> {
            SellerEntity seller = sellerRepository.findById(sellerId).orElseThrow();
            seller.setStore(store);
            store.addSeller(seller);
            return store;
        }).toList());
    }

    public StoreEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new StoreNotFoundException("Store with id " + id + " not found"));
    }

    public List<StoreEntity> findAll() {
        return repository.findAll();
    }
}

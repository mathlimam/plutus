package tech.mlm.plutus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.StoreDTO;
import tech.mlm.plutus.dtos.requests.AddSellerRequestDTO;
import tech.mlm.plutus.dtos.requests.CreateStoreDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.exceptions.StoreNotFoundException;
import tech.mlm.plutus.mappers.StoreMapper;
import tech.mlm.plutus.repositories.StoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository repository;
    private final StoreMapper storeMapper;
    private final SellerService sellerService;

    public boolean existsSellerByIdAndStoreId(Long storeId, Long sellerId) {
        return repository.existsBySellerAndStoreId(sellerId, storeId);
    }

    public StoreEntity save(StoreEntity store) {
        return repository.save(store);
    }

    public List<StoreEntity> save(List<StoreEntity> stores) {
        return repository.saveAll(stores);
    }

    public StoreDTO createStore(CreateStoreDTO request) {
        StoreEntity store = new StoreEntity();
        store.setName(request.name());
        return storeMapper.toDTO(repository.save(store));
    }

    public StoreDTO addSeller(AddSellerRequestDTO request) {
        StoreEntity store = findById(request.storeId());
        SellerEntity seller = sellerService.findById(request.sellerId());
        store.addSeller(seller);
        seller.setStore(store);
        return storeMapper.toDTO(repository.save(store));
    }

    public StoreDTO addSeller(Long storeId, List<Long> sellerIds) {
        StoreEntity store = findById(storeId);
        List<SellerEntity> sellers = sellerIds.stream()
                .map(sellerService::findById)
                .toList();

        sellers.forEach(seller -> {
            if (existsSellerByIdAndStoreId(storeId, seller.getId())) {
                throw new IllegalArgumentException("Seller with id " + seller.getId() + " already exists in store with id " + storeId);
            }
            seller.setStore(store);
            store.addSeller(seller);
        });
        return storeMapper.toDTO(repository.save(store);)
    }

    public StoreEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new StoreNotFoundException("Store with id " + id + " not found"));
    }

    public List<StoreEntity> findAll() {
        return repository.findAll();
    }
}

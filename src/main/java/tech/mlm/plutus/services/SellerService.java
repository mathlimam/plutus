package tech.mlm.plutus.services;

import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.SellerDTO;
import tech.mlm.plutus.dtos.requests.CreateSellerDTO;
import tech.mlm.plutus.entities.SellerEntity;
import tech.mlm.plutus.exceptions.SellerNotFoundException;
import tech.mlm.plutus.mappers.SellerMapper;
import tech.mlm.plutus.repositories.SellerRepository;
import tech.mlm.plutus.repositories.StoreRepository;

import java.util.List;


@Service
public class SellerService {

    private final SellerRepository repository;
    private final SellerMapper mapper;
    private final StoreService storeService;

    public SellerService(SellerRepository repository, SellerMapper mapper, StoreService storeService) {
        this.repository = repository;
        this.mapper = mapper;
        this.storeService = storeService;
    }

    public SellerDTO create(CreateSellerDTO dto){
        var seller = new SellerEntity();
        seller.setName(dto.name());
        return mapper.toDTO(repository.save(seller));
    }
    public SellerEntity save(SellerEntity entity) {
        return repository.save(entity);
    }

    public List<SellerDTO> findAll() {
        return mapper.toDTO(repository.findAll());
    }

    public SellerDTO findById(Long id) {
        return mapper.toDTO(findEntityById(id));
    }

    public SellerEntity findEntityById(Long id){
        return repository.findById(id).orElseThrow(() -> new SellerNotFoundException("Seller with id " + id + " not found"));
    }

    public SellerDTO setSellerStore(Long sellerId, Long storeId) {
        var seller = repository.findById(sellerId).orElseThrow();
        seller.setStore(storeService.findById(storeId));
        return mapper.toDTO(repository.save(seller));
    }
}

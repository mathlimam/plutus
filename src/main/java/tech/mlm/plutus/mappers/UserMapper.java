package tech.mlm.plutus.mappers;

import org.springframework.stereotype.Component;
import tech.mlm.plutus.dtos.UserDTO;
import tech.mlm.plutus.entities.UserEntity;

@Component
public class UserMapper {
    private final StoreMapper storeMapper;

    public UserMapper(StoreMapper storeMapper){
        this.storeMapper = storeMapper;
    }

    public UserDTO toDTO(UserEntity userEntity){
        return new UserDTO(userEntity.getUsername(),
                userEntity.getPassword(),
                storeMapper.toDTO(userEntity.getStore()));
    }
}

package tech.mlm.plutus.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.mlm.plutus.dtos.UserDTO;
import tech.mlm.plutus.dtos.requests.UserRegistrationDTO;
import tech.mlm.plutus.entities.StoreEntity;
import tech.mlm.plutus.entities.UserEntity;
import tech.mlm.plutus.exceptions.DuplicatedUsernameException;
import tech.mlm.plutus.exceptions.UserNotFoundException;
import tech.mlm.plutus.mappers.UserMapper;
import tech.mlm.plutus.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final StoreService storeService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, StoreService storeService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.storeService = storeService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserRegistrationDTO dto){
        if(userRepository.existsByUsername(dto.username())) throw new DuplicatedUsernameException();
        StoreEntity storeEntity = storeService.findById(dto.storeId());

        UserEntity user = new UserEntity(dto.username(),
                              passwordEncoder.encode(dto.password()),
                              storeEntity,
                              dto.role());

        return (userMapper.toDTO(userRepository.save(user)));
    }

    public boolean authenticateUser(String username, String rawPassword){
        UserEntity userEntity = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
        return passwordEncoder.matches(rawPassword, userEntity.getPassword());
    }
}

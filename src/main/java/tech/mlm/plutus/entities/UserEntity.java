package tech.mlm.plutus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tech.lastbox.lastshield.security.core.annotations.Password;
import tech.lastbox.lastshield.security.core.annotations.Username;
import tech.mlm.plutus.utils.types.Roles;


@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Setter
    @Getter
    @Column(unique=true)
    @Username
    private String username;

    @Setter
    @Getter
    @Column
    @Password
    @JsonIgnore
    private String password;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @JsonIgnore
    private String role;

    public UserEntity() {}

    public UserEntity(String username, String password){
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

    public UserEntity(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }


}

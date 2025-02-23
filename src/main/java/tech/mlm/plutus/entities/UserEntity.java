package tech.mlm.plutus.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import tech.lastbox.lastshield.security.core.annotations.Password;
import tech.lastbox.lastshield.security.core.annotations.Username;
import tech.mlm.plutus.utils.types.Role;


@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique=true)
    @Username
    private String username;

    @Column
    @Password
    @JsonIgnore
    private String password;

    @OneToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @JsonIgnore
    private Role role;

    public UserEntity() {}

    public UserEntity(String username, String password, StoreEntity store){
        this.username = username;
        this.password = password;
        this.store = store;
        this.role = Role.USER;
    }

    public UserEntity(String username, String password, StoreEntity store, Role role){
        this.username = username;
        this.password = password;
        this.store = store;
        this.role = role;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }
}

package tech.mlm.plutus.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany
    private final Set<SellerEntity> sellers = new HashSet<>();

    public StoreEntity() {}

    public StoreEntity(String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Long getId () {
        return id;
    }

    public Set<SellerEntity> getSellers () {
        return sellers;
    }

    public void addSeller(SellerEntity seller) {
        sellers.add(seller);
    }
}

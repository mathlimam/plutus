package tech.mlm.plutus.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany
    private final List<SellerEntity> sellers = new ArrayList<>();

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

    public List<SellerEntity> getSellers () {
        return sellers;
    }

    public void addSeller(SellerEntity seller) {
        sellers.add(seller);
    }
}

package tech.mlm.plutus.entities;

import jakarta.persistence.*;
import jdk.dynalink.Operation;
import org.apache.catalina.Store;

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

    public String getname () {
        return name;
    }

    public void setname (String name) {
        this.name = name;
    }

    public List<SellerEntity> getSellers () {
        return sellers;
    }

    public void addSeller(SellerEntity seller) {
        sellers.add(seller);
    }
}

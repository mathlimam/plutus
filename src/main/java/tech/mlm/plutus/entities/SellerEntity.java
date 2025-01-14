package tech.mlm.plutus.entities;

import jakarta.persistence.*;

@Entity
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public SellerEntity() {}

    public SellerEntity(String name, StoreEntity store) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}

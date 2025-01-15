package tech.mlm.plutus.entities;

import jakarta.persistence.*;

@Entity
public class ProductEntity {
    @Id
    private String barcode;

    @Column(nullable = false)
    private String name;

    public ProductEntity() {}

    public ProductEntity(String barcode, String name) {
        this.barcode = barcode;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}



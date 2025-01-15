package tech.mlm.plutus.entities;

import jakarta.persistence.*;

@Entity
public class SellerEntity {
    @Id
    private String cpf;

    @Column(nullable = false)
    private String name;

    public SellerEntity() {}

    public SellerEntity(String cpf, String name) {
        this.cpf = cpf;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCpf(){
        return this.cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }
}

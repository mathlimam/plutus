package tech.mlm.plutus.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {
    @Id
    @Column(unique = true)
    @NotNull
    private String barcode;

    @Column(name = "product_name")
    @NotNull
    private String name;
}



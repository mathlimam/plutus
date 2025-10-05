package tech.mlm.plutus.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SellerEntity extends DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="seller_name")
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;
}

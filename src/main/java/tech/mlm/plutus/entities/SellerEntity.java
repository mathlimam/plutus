package tech.mlm.plutus.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="seller_name")
    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;
}

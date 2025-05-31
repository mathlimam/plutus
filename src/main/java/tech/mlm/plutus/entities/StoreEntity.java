package tech.mlm.plutus.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @NonNull
    private final Set<SellerEntity> sellers = new HashSet<>();

    @OneToMany(mappedBy="originStore")
    @NonNull
    private final List<OperationEntity> originOperations = new ArrayList<>();

    @OneToMany(mappedBy="destinationStore")
    @NonNull
    private final List<OperationEntity> destinationOperations = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
    public void addSeller(SellerEntity seller) {
        sellers.add(seller);
    }
}

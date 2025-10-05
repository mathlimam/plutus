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
@NoArgsConstructor
public class StoreEntity extends DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private final Set<SellerEntity> sellers = new HashSet<>();

    @OneToMany(mappedBy="originStore")
    @NotNull
    private final List<OperationEntity> originOperations = new ArrayList<>();

    @OneToMany(mappedBy="destinationStore")
    @NotNull
    private final List<OperationEntity> destinationOperations = new ArrayList<>();

    @OneToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
    public void addSeller(SellerEntity seller) {
        sellers.add(seller);
    }
}

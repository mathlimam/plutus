package tech.mlm.plutus.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.mlm.plutus.exceptions.InvalidProductQuantityException;
import tech.mlm.plutus.utils.types.OperationType;
import tech.mlm.plutus.utils.types.StatusType;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="operation_type")
    @NonNull
    private OperationType type;

    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.PENDING;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NonNull
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "origin_store_id")
    @NonNull
    private StoreEntity originStore;

    @ManyToOne
    @JoinColumn(name = "destination_store_id")
    @NonNull
    private StoreEntity destinationStore;

    @ManyToOne
    @JoinColumn(name = "origin_seller_id")
    @NonNull
    private SellerEntity originSeller;

    @ManyToOne
    @JoinColumn(name = "destination_seller_id")
    @NonNull
    private SellerEntity destinationSeller;

    @Column(name="quantity")
    @NonNull
    private int quantity;

    @Column(name="created_at")
    @NonNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="invoice_number")
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "isconcludedBy_seller_id")
    private SellerEntity concludedBy;

    public OperationEntity(OperationType type, ProductEntity productEntity, StoreEntity originStore, StoreEntity destinationStore,
                           SellerEntity originSeller, SellerEntity destinationSeller, int quantity) {
        this.type = type;
        this.productEntity = productEntity;
        this.originStore = originStore;
        this.destinationStore = destinationStore;
        this.originSeller = originSeller;
        this.destinationSeller = destinationSeller;
        this.quantity = quantity;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public OperationEntity setOperationType(OperationType operationType) {
        this.setType(operationType);
        return this;
    }

    public OperationEntity setOperationStatus(StatusType status) {
        this.setStatus(status);
        return this;
    }

    public OperationEntity setOperationInvoice(String invoice){
        this.setInvoiceNumber(invoice);
        return this;
    }

    public OperationEntity setOperationQuantity(int quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new InvalidProductQuantityException("A quantidade deve ser maior que zero.");
        this.quantity = quantity;
    }
}

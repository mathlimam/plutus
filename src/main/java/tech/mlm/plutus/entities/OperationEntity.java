package tech.mlm.plutus.entities;

import jakarta.persistence.*;
import tech.mlm.plutus.types.OperationType;
import tech.mlm.plutus.types.StatusType;

import java.time.LocalDateTime;

@Entity
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.PENDING;

    @ManyToOne
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "origin_store_id", nullable = false)
    private StoreEntity originStore;

    @ManyToOne
    @JoinColumn(name = "destination_store_id", nullable = false)
    private StoreEntity destinationStore;

    @ManyToOne
    @JoinColumn(name = "origin_seller_id", nullable = false)
    private SellerEntity originSeller;

    @ManyToOne
    @JoinColumn(name = "destination_seller_id", nullable = false)
    private SellerEntity destinationSeller;

    private int quantity;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "sconcludedBy_seller_id")
    private SellerEntity concludedBy;

    public OperationEntity() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
        setUpdatedAt(LocalDateTime.now());
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public SellerEntity getConcludedBy() {
        return concludedBy;
    }

    public void setConcludedBy(SellerEntity concludedBy) {
        this.concludedBy = concludedBy;
        setUpdatedAt(LocalDateTime.now());
    }

    public StoreEntity getOriginStore() {
        return originStore;
    }

    public void setOriginStore(StoreEntity originStore) {
        this.originStore = originStore;
    }

    public StoreEntity getDestinationStore() {
        return destinationStore;
    }

    public void setDestinationStore(StoreEntity destinationStore) {
        this.destinationStore = destinationStore;
    }

    public SellerEntity getOriginSeller() {
        return originSeller;
    }

    public void setOriginSeller(SellerEntity originSeller) {
        this.originSeller = originSeller;
    }

    public SellerEntity getDestinationSeller() {
        return destinationSeller;
    }

    public void setDestinationSeller(SellerEntity destinationSeller) {
        this.destinationSeller = destinationSeller;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantity = quantity;
    }

}

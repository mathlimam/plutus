package tech.mlm.plutus.utils.types;

public enum LabelsEnum {
    SELLER("Seller"), STORE("Store");

    final String name;

    LabelsEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

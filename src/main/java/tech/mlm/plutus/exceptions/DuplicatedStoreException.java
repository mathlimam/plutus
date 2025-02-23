package tech.mlm.plutus.exceptions;

public class DuplicatedStoreException extends RuntimeException {
    public DuplicatedStoreException(String message) {
        super(message);
    }
}

package tech.mlm.plutus.exceptions;

public class SellerNotFoundException extends RuntimeException {
  public SellerNotFoundException(String message) {
    super(message);
  }
}

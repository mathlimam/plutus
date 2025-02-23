package tech.mlm.plutus.exceptions;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException() {
        super("Já existe um usuário com esse username.");
    }
}

package app.exception;

public class TransactionException {
    public static class TransactionNotFoundException extends RuntimeException {
        public TransactionNotFoundException(String message) {
            super(message);
        }
    }

    public static class AccountException extends RuntimeException {
        public AccountException(String message) {
            super(message);
        }
    }
}

package app.exception;

public class TransactionException {
    public static class TransactionNotFoundException extends RuntimeException {
        public TransactionNotFoundException(String message) {
            super(message);
        }
    }
}

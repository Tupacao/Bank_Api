package app.exception;

public class LogTransactionException {
    public static class LogTransactionNotFoundException extends RuntimeException {
        public LogTransactionNotFoundException(String message) {
            super(message);
        }
    }
}

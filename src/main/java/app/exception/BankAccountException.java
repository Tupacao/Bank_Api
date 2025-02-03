package app.exception;

public class BankAccountException {
    public static class BankAccountNotFoundException extends RuntimeException {
        public BankAccountNotFoundException(String message) {
            super(message);
        }
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}

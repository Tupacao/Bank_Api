package app.exception;

public class BankAccountException {
    public static class BankAccountNotFoundException extends RuntimeException {
        public BankAccountNotFoundException(String message) {
            super(message);
        }
    }
}

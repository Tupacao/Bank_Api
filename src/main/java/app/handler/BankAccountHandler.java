package app.handler;

import app.exception.BankAccountException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces(io.micronaut.http.MediaType.APPLICATION_JSON)
@Singleton
public class BankAccountHandler {

    @Singleton
    public static class BankAccountNotFoundHandler implements ExceptionHandler<BankAccountException.BankAccountNotFoundException, HttpResponse<?>> {
        @Override
        public HttpResponse<?> handle(HttpRequest request, BankAccountException.BankAccountNotFoundException exception) {
            return HttpResponse.notFound(exception.getMessage());
        }
    }

    @Singleton
    public static class InsufficientFundsHandler implements ExceptionHandler<BankAccountException.InsufficientFundsException, HttpResponse<?>> {
        @Override
        public HttpResponse<?> handle(HttpRequest request, BankAccountException.InsufficientFundsException exception) {
            return HttpResponse.notFound(exception.getMessage());
        }
    }
}
package app.handler;

import app.exception.TransactionException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces(io.micronaut.http.MediaType.APPLICATION_JSON)
@Singleton
public class TransactionHandler {

    @Singleton
    public static class TransactionNotFoundHandler implements ExceptionHandler<TransactionException.TransactionNotFoundException, HttpResponse<?>> {
        @Override
        public HttpResponse<?> handle(HttpRequest request, TransactionException.TransactionNotFoundException exception) {
            return HttpResponse.notFound(exception.getMessage());
        }
    }

    @Singleton
    public static class OriginDestinationAccountHandler implements ExceptionHandler<TransactionException.AccountException, HttpResponse<?>> {
        @Override
        public HttpResponse<?> handle(HttpRequest request, TransactionException.AccountException exception) {
            return HttpResponse.badRequest(exception.getMessage());
        }
    }
}

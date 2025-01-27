package app.handler;

import app.exception.LogTransactionException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces(io.micronaut.http.MediaType.APPLICATION_JSON)
@Singleton
public class LogHandler {
    @Singleton
    public static class LogNotFoundHandler implements ExceptionHandler<LogTransactionException.LogTransactionNotFoundException, HttpResponse<?>> {
        @Override
        public HttpResponse<?> handle(HttpRequest request, LogTransactionException.LogTransactionNotFoundException exception) {
            return HttpResponse.notFound(exception.getMessage());
        }
    }
}
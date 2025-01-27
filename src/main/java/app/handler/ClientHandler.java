package app.handler;

import app.exception.ClientException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces(io.micronaut.http.MediaType.APPLICATION_JSON)
@Singleton
public class ClientHandler {

    @Singleton
    public static class ClientNotFoundHandler implements ExceptionHandler<ClientException.ClientNotFoundException, HttpResponse<?>> {
        @Override
        public HttpResponse<?> handle(HttpRequest request, ClientException.ClientNotFoundException exception) {
            return HttpResponse.notFound(exception.getMessage());
        }
    }
}

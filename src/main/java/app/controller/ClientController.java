package app.controller;

import app.model.Client;
import app.service.ClientService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Delete;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/client")
@Tag(name = "Client")
public class ClientController {

    @Inject
    private ClientService clientService;

    @Get("/{id}")
    public HttpResponse<Client> getClient(@PathVariable Long id) {
        return HttpResponse.ok(clientService.getClient(id));
    }

    @Get("/all")
    public HttpResponse<List<Client>> getAllClients() {
        return HttpResponse.ok(clientService.getAllClients());
    }

    @Post("/")
    public HttpResponse<Client> createClient(@Body @Valid Client client) {
        return HttpResponse.created(clientService.createClient(client));
    }

    @Put("/{id}")
    public HttpResponse<Client> updateClient(@Body @Valid Client client, @PathVariable Long id) {
        return HttpResponse.ok(clientService.updateClient(client, id));
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return HttpResponse.noContent();
    }
}
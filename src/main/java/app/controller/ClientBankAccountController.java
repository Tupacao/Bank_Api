package app.controller;

import app.model.BankAccount;
import app.service.ClienBankAccountService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/client/{clientId}/account")
@Tag(name = "ClientBankAccount")
public class ClientBankAccountController {

    @Inject
    ClienBankAccountService clientBankAccountService;

    @Get("/")
    public HttpResponse<List<BankAccount>> getClientBankAccounts(@PathVariable Long clientId) {
        return HttpResponse.ok(clientBankAccountService.getClientBankAccounts(clientId));
    }

    @Post("/")
    public HttpResponse<BankAccount> createClientBankAccount(@PathVariable Long clientId, @Body @Valid BankAccount bankAccount) {
           return HttpResponse.created(clientBankAccountService.createClientBankAccount(clientId, bankAccount));
    }
}

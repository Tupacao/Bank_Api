package app.controller;

import app.model.BankAccount;
import app.service.BankAccountService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/transaction")
@Tag(name = "Transaction")
public class TransactionController {
    @Inject
    private BankAccountService bankAccountService;

    @Get("/{id}")
    public HttpResponse<BankAccount> getBankAccount(@PathVariable Long id) {
        return HttpResponse.ok(bankAccountService.getBankAccount(id));
    }

    @Get("/all")
    public HttpResponse<List<BankAccount>> getAllBankAccounts() {
        return HttpResponse.ok(bankAccountService.getAllBankAccounts());
    }

    @Post("/")
    public HttpResponse<BankAccount> createBankAccount(@Body @Valid BankAccount client) {
        return HttpResponse.created(bankAccountService.createBankAccount(client));
    }

    @Put("/{id}")
    public HttpResponse<BankAccount> updateBankAccount(@Body @Valid BankAccount client, @PathVariable Long id) {
        return HttpResponse.ok(bankAccountService.updateBankAccount(client, id));
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return HttpResponse.noContent();
    }
}

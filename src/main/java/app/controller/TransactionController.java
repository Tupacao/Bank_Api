package app.controller;

import app.model.Transaction;
import app.service.TransactionService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.PathVariable;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/transaction")
@Tag(name = "Transaction")
public class TransactionController {
    @Inject
    private TransactionService bankAccountService;

    @Get("/{id}")
    public HttpResponse<Transaction> getTransaction(@PathVariable Long id) {
        return HttpResponse.ok(bankAccountService.getTransaction(id));
    }

    @Get("/all")
    public HttpResponse<List<Transaction>> getAllTransactions() {
        return HttpResponse.ok(bankAccountService.getAllTransactions());
    }

    @Post("/")
    public HttpResponse<Transaction> createTransaction(@Body @Valid Transaction transaction) {
        return HttpResponse.created(bankAccountService.createTransaction(transaction));
    }

    @Put("/{id}")
    public HttpResponse<Transaction> updateTransaction(@Body @Valid Transaction transaction, @PathVariable Long id) {
        return HttpResponse.ok(bankAccountService.updateTransaction(transaction, id));
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteTransaction(@PathVariable Long id) {
        bankAccountService.deleteTransaction(id);
        return HttpResponse.noContent();
    }
}
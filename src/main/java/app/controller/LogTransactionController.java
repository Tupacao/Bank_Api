package app.controller;

import app.dto.request.TransactionDTO;
import app.service.LogTransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@Controller("/log")
public class LogTransactionController {

    @Inject
    private LogTransactionService logTransactionService;

    @Post("/{id}")
    public HttpResponse<?> createLogTransaction(@PathVariable Long id, @Body @Valid TransactionDTO transactionDTO) {
        return HttpResponse.ok(logTransactionService.createLogTransaction(transactionDTO, id));
    }
}

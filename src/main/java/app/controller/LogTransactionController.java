package app.controller;

import app.dto.request.TransactionDTO;
import app.model.Transaction;
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

    @Post("/{origin_id}/{destination_id}")
    public HttpResponse<Transaction> createLogTransaction(@PathVariable Long origin_id, @PathVariable Long destination_id, @Body @Valid TransactionDTO transactionDTO) {
        return HttpResponse.ok(logTransactionService.createLogTransaction(transactionDTO, origin_id, destination_id));
    }
}

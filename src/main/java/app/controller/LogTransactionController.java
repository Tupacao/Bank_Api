package app.controller;

import app.model.LogTransaction;
import app.service.LogTransactionService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/log")
@Tag(name = "LogTransaction")
public class LogTransactionController {
    @Inject
    private LogTransactionService logTransactionService;

    @Get("/{id}")
    public HttpResponse<LogTransaction> getLogTransaction(@PathVariable Long id) {
        return HttpResponse.ok(logTransactionService.getLogTransaction(id));
    }

    @Get("/all")
    public HttpResponse<List<LogTransaction>> getAllLogTransactions() {
        return HttpResponse.ok(logTransactionService.getAllLogTransactions());
    }

    @Post("/")
    public HttpResponse<LogTransaction> createLogTransaction(@Body @Valid LogTransaction client) {
        return HttpResponse.created(logTransactionService.createLogTransaction(client));
    }

    @Put("/{id}")
    public HttpResponse<LogTransaction> updateLogTransaction(@Body @Valid LogTransaction client, @PathVariable Long id) {
        return HttpResponse.ok(logTransactionService.updateLogTransaction(client, id));
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteLogTransaction(@PathVariable Long id) {
        logTransactionService.deleteLogTransaction(id);
        return HttpResponse.noContent();
    }
}

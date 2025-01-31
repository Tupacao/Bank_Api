package app.controller;

import app.model.Log;
import app.service.LogService;

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

@Controller("/log")
@Tag(name = "LogTransaction")
public class LogController {
    @Inject
    private LogService logTransactionService;

    @Get("/{id}")
    public HttpResponse<Log> getLogTransaction(@PathVariable String id) {
        return HttpResponse.ok(logTransactionService.getLogTransaction(id));
    }

    @Get("/all")
    public HttpResponse<List<Log>> getAllLogTransactions() {
        return HttpResponse.ok(logTransactionService.getAllLogTransactions());
    }

    @Post("/")
    public HttpResponse<Log> createLogTransaction(@Body @Valid Log logTransaction) {
        return HttpResponse.created(logTransactionService.createLogTransaction(logTransaction));
    }

    @Put("/{id}")
    public HttpResponse<Log> updateLogTransaction(@Body @Valid Log logTransaction, @PathVariable String id) {
        return HttpResponse.ok(logTransactionService.updateLogTransaction(logTransaction, id));
    }

    @Delete("/{id}")
    public HttpResponse<?> deleteLogTransaction(@PathVariable String id) {
        logTransactionService.deleteLogTransaction(id);
        return HttpResponse.noContent();
    }
}

package io.karmanov.tms.controller;

import io.karmanov.tms.domain.Transaction;
import io.karmanov.tms.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/transactions")
@Api(description = "Provides operation for saving transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Add new transaction")
    public void save(@RequestBody Transaction transaction) {
        transactionService.addTransaction(transaction);
    }
}

package io.karmanov.tms.service;

import io.karmanov.tms.domain.Transaction;
import io.karmanov.tms.exception.TransactionTooOldException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.karmanov.tms.utils.TimeUtils.isOlderThenTimeLimit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final StatisticsService statisticsService;

    public void addTransaction(Transaction transaction) {
        log.info("Adding new transaction {}", transaction);
        if (isOlderThenTimeLimit(transaction.getTimestamp())) {
            log.error("Transaction [{}] is too old for processing", transaction);
            throw new TransactionTooOldException();
        }
        statisticsService.addTransaction(transaction);
    }
}

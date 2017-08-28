package io.karmanov.tms.service;

import io.karmanov.tms.domain.Transaction;
import io.karmanov.tms.exception.TransactionTooOldException;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private TransactionService transactionService;

    @Before
    public void setUp() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(1503532532000L);
    }

    @After
    public void tearDown() throws Exception {
        DateTimeUtils.currentTimeMillis();
    }

    @Test
    public void create_success() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(1.0));
        transaction.setTimestamp(1503532531L);
        transactionService.addTransaction(transaction);
        verify(statisticsService, times(1)).addTransaction(transaction);
    }

    @Test(expected = TransactionTooOldException.class)
    public void create_too_old() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(1.0));
        transaction.setTimestamp(1503532310L);
        transactionService.addTransaction(transaction);
        verify(statisticsService, never()).addTransaction(transaction);
    }
}
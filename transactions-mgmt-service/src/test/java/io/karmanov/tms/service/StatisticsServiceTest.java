package io.karmanov.tms.service;

import io.karmanov.tms.domain.Statistics;
import io.karmanov.tms.domain.Transaction;
import io.karmanov.tms.service.StatisticsService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static io.karmanov.tms.StubDataBuilder.getTransaction;
import static io.karmanov.tms.utils.StatisticsUtils.round;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    private StatisticsService statisticsService = new StatisticsService();

    @Test
    public void getStatistics_single_transaction() throws Exception {
        BigDecimal expectedSingleValue = round(new BigDecimal(15.97));
        Transaction transaction = new Transaction();
        transaction.setAmount(expectedSingleValue);
        transaction.setTimestamp(1503620600);

        statisticsService.addTransaction(transaction);

        Statistics statistics = statisticsService.getStatistics();
        assertEquals(expectedSingleValue, statistics.getSum());
        assertEquals(expectedSingleValue, statistics.getMax());
        assertEquals(expectedSingleValue, statistics.getMin());
        assertEquals(expectedSingleValue, statistics.getAvg());
    }

    @Test
    public void getStatistics_multipleTransactions() throws Exception {
        BigDecimal expectedSum = new BigDecimal(38.88).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedMax = new BigDecimal(15.01).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedMin = new BigDecimal(10).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedAvg = new BigDecimal(12.96).setScale(2, RoundingMode.HALF_UP);

        DateTime t1 = DateTime.now().minusMinutes(10);
        DateTime t2 = DateTime.now().minusMinutes(11);
        DateTime t3 = DateTime.now().minusMinutes(9);

        statisticsService.addTransaction(getTransaction(new BigDecimal(13.87), t1));
        statisticsService.addTransaction(getTransaction(new BigDecimal(10), t2));
        statisticsService.addTransaction(getTransaction(new BigDecimal(15.01), t3));

        Statistics statistics = statisticsService.getStatistics();
        assertEquals(expectedSum, statistics.getSum());
        assertEquals(expectedMax, statistics.getMax());
        assertEquals(expectedMin, statistics.getMin());
        assertEquals(expectedAvg, statistics.getAvg());
    }

    @Test
    public void emptyStatistics() throws Exception {
        Statistics statistics = statisticsService.getStatistics();
        assertEquals(BigDecimal.ZERO, statistics.getSum());
        assertEquals(BigDecimal.ZERO, statistics.getMax());
        assertEquals(BigDecimal.ZERO, statistics.getMin());
        assertEquals(BigDecimal.ZERO, statistics.getAvg());
        assertEquals(0, statistics.getCount());
    }
}
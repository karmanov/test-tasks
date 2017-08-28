package io.karmanov.tms.service;

import io.karmanov.tms.domain.Statistics;
import io.karmanov.tms.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.karmanov.tms.utils.StatisticsUtils.*;
import static io.karmanov.tms.utils.TimeUtils.isOlderThenTimeLimit;


@Slf4j
@Service
public class StatisticsService {

    private Map<Long, Statistics> statisticsMap = new ConcurrentHashMap<>(60);

    public Statistics getStatistics() {
        Collection<Statistics> statisticsList = statisticsMap.values();
        BigDecimal sum = getSum(statisticsList);
        BigDecimal max = getMax(statisticsList);
        BigDecimal min = getMin(statisticsList);
        BigDecimal avg = getAvg(statisticsList, sum);
        return new Statistics(sum, avg, max, min, statisticsList.size());
    }

    @Async("threadPoolTaskExecutor")
    public void addTransaction(Transaction transaction) {
        Statistics statistics = statisticsMap.getOrDefault(transaction.getTimestamp(), new Statistics());
        try {
            updateStatistics(statistics, transaction);
        } catch (Exception e) {
            log.error("Oops, something went wrong during saving transaction. Reason: {}", e.getMessage());
        }
    }

    private void updateStatistics(Statistics statistics, Transaction transaction) {
        int count = statistics.getCount() + 1;
        BigDecimal sum = getSum(statistics.getSum(), transaction.getAmount());
        BigDecimal avg = getAvg(sum, count);
        BigDecimal max = getMax(statistics.getMax(), transaction.getAmount());
        BigDecimal min = getMin(statistics.getMin(), transaction.getAmount());
        statisticsMap.put(transaction.getTimestamp(), new Statistics(sum, avg, max, min, count));
    }

    @Scheduled(fixedDelay = 1000)
    public void evictStaleStatistics() {
        for (Long timestamp : statisticsMap.keySet()) {
            if (isOlderThenTimeLimit(timestamp)) {
                log.info("Evicting transaction for timestamp {}", timestamp);
                statisticsMap.remove(timestamp);
            }
        }
    }

}

package io.karmanov.tms.utils;

import io.karmanov.tms.domain.Statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;

import static java.util.Comparator.comparing;
import static org.springframework.util.CollectionUtils.isEmpty;

public class StatisticsUtils {

    public static BigDecimal getSum(BigDecimal b1, BigDecimal b2) {
        return round(b1.add(b2));
    }

    public static BigDecimal getAvg(BigDecimal sum, int count) {
        return sum.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getMax(BigDecimal b1, BigDecimal b2) {
        return round(b1.max(b2));
    }

    public static BigDecimal getMin(BigDecimal b1, BigDecimal b2) {
        if (BigDecimal.ZERO.equals(b1)) {
            return round(b2);
        }
        return round(b1.min(b2));
    }

    public static BigDecimal getAvg(Collection<Statistics> statistics, BigDecimal sum) {
        if (isEmpty(statistics)) {
            return BigDecimal.ZERO;
        }
        return sum.divide(new BigDecimal(statistics.size()), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getSum(Collection<Statistics> statistics) {
        if (isEmpty(statistics)) {
            return BigDecimal.ZERO;
        }
        return statistics.parallelStream()
                         .map(Statistics::getSum)
                         .reduce(BigDecimal.ZERO, BigDecimal::add)
                         .setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getMax(Collection<Statistics> statistics) {
        if (isEmpty(statistics)) {
            return BigDecimal.ZERO;
        }
        return round(statistics.parallelStream().max(byMax()).get().getMax());
    }

    public static BigDecimal getMin(Collection<Statistics> statistics) {
        if (isEmpty(statistics)) {
            return BigDecimal.ZERO;
        }
        return round(statistics.parallelStream().min(byMin()).get().getMin());
    }

    private static Comparator<Statistics> byMin() {
        return comparing(Statistics::getMin);
    }

    private static Comparator<Statistics> byMax() {
        return comparing(Statistics::getMax);
    }

    public static BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}

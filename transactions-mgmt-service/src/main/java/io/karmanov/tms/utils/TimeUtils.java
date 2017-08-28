package io.karmanov.tms.utils;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class TimeUtils {

    private static final int TIME_LIMIT = 60;

    public static boolean isOlderThenTimeLimit(long timestamp) {
        DateTime transactionDateTime = new DateTime(timestamp * 1000);
        int seconds = Seconds.secondsBetween(transactionDateTime, DateTime.now()).getSeconds();
        return seconds > TIME_LIMIT;
    }
}

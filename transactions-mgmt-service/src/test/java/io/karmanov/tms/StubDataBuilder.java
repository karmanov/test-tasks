package io.karmanov.tms;

import io.karmanov.tms.domain.Transaction;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class StubDataBuilder {

    public static Transaction getTransaction(BigDecimal amount, DateTime timestamp) {
        return new Transaction(amount, timestamp.getMillis() / 1000);
    }
}

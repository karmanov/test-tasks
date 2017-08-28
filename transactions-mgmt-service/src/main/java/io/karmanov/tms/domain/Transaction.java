package io.karmanov.tms.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.karmanov.tms.serializer.AmountSerializer;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @JsonSerialize(using = AmountSerializer.class)
    private BigDecimal amount;
    private long timestamp;
}

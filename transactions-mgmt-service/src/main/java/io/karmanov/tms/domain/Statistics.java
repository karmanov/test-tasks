package io.karmanov.tms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal avg = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.ZERO;
    private int count;
}

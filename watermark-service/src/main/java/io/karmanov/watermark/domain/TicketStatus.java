package io.karmanov.watermark.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Optional;

/**
 * Possible {@link io.karmanov.watermark.domain.Ticket} statuses
 */

@RequiredArgsConstructor
public enum TicketStatus {
    COMPLETE(1), IN_PROGRESS(2);

    private final Integer code;

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public static Optional<TicketStatus> fromCode(Integer code) {
        return EnumSet.allOf(TicketStatus.class).stream().filter(t -> t.getCode().equals(code)).findFirst();
    }

    @JsonCreator
    public static TicketStatus valueOf(Integer code) {
        return fromCode(code).orElse(null);
    }

}

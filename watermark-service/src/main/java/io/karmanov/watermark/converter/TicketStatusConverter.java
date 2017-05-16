package io.karmanov.watermark.converter;

import io.karmanov.watermark.domain.TicketStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.validation.constraints.NotNull;

/**
 * Converter for {@link TicketStatus} enum value to {@link Integer} code
 */
@Converter
public class TicketStatusConverter implements AttributeConverter<TicketStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(@NotNull TicketStatus ticketStatus) {
        return ticketStatus.getCode();
    }

    @Override
    public TicketStatus convertToEntityAttribute(@NotNull Integer dbData) {
        return TicketStatus.valueOf(dbData);
    }
}

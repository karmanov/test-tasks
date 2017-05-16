package io.karmanov.watermark.converter;

import io.karmanov.watermark.domain.Ticket;
import io.karmanov.watermark.dto.TicketDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for {@link Ticket} entity to {@link TicketDTO} transformation
 */
@Component
public class TicketDomainToDTOConverter implements Converter<Ticket, TicketDTO> {

    @Override
    public TicketDTO convert(final Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Could not convert null to TicketDTO");
        }
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO);
        return ticketDTO;
    }
}

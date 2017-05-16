package io.karmanov.watermark.service;

import io.karmanov.watermark.converter.ConverterService;
import io.karmanov.watermark.domain.Ticket;
import io.karmanov.watermark.domain.TicketStatus;
import io.karmanov.watermark.dto.DocumentDTO;
import io.karmanov.watermark.dto.TicketDTO;
import io.karmanov.watermark.exception.TicketNotFoundException;
import io.karmanov.watermark.exception.WatermarkingAlreadyInProgressException;
import io.karmanov.watermark.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static io.karmanov.watermark.domain.TicketStatus.COMPLETE;
import static io.karmanov.watermark.domain.TicketStatus.IN_PROGRESS;
import static java.util.UUID.randomUUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final DocumentService documentService;

    private final ConverterService converterService;

    public TicketDTO createTicket(Long documentId) {
        log.info("Creating ticket to watermark document with id {}", documentId);
        validate(documentId);
        Ticket ticket = new Ticket();
        ticket.setId(randomUUID());
        ticket.setStatus(IN_PROGRESS);
        ticket.setDocumentId(documentId);
        return convert(ticketRepository.saveAndFlush(ticket));
    }

    public TicketDTO getById(UUID ticketId) {
        log.debug("Fetching ticket by id {}", ticketId);
        TicketDTO ticket = convert(ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId.toString())));
        if (COMPLETE.equals(ticket.getStatus())) {
            DocumentDTO document = documentService.getDocumentById(ticket.getDocumentId());
            ticket.setDocument(document);
        }
        return ticket;
    }

    public TicketDTO update(TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketDTO.getId())
                                        .orElseThrow(() -> new TicketNotFoundException(ticketDTO.getId().toString()));
        BeanUtils.copyProperties(ticketDTO, ticket);
        log.info("Updating ticket ticket [{}] with new values [{}]", ticket, ticketDTO);
        return convert(ticketRepository.saveAndFlush(ticket));
    }

    private TicketDTO convert(Ticket ticket) {
        return converterService.convert(ticket, TicketDTO.class);
    }

    private void validate(Long documentId) {
        Optional<Ticket> ticketOpt = ticketRepository.findOneByDocumentIdAndStatus(documentId, TicketStatus.IN_PROGRESS);
        ticketOpt.ifPresent(t -> {
            throw new WatermarkingAlreadyInProgressException(t.getId().toString());
        });
    }

}

package io.karmanov.watermark.service;

import io.karmanov.watermark.dto.DocumentDTO;
import io.karmanov.watermark.dto.TicketDTO;
import io.karmanov.watermark.dto.WatermarkRequestDTO;
import io.karmanov.watermark.service.processor.AsyncWatermarkProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatermarkService {

    private final DocumentService documentService;

    private final AsyncWatermarkProcessor asyncWatermarkProcessor;

    private final TicketService ticketService;

    public TicketDTO watermark(WatermarkRequestDTO request) {
        log.info("Watermarking document with id {}", request.getDocumentId());
        DocumentDTO document = documentService.getDocumentById(request.getDocumentId());
        TicketDTO ticket = ticketService.createTicket(request.getDocumentId());
        asyncWatermarkProcessor.watermarkDocument(document, ticket);
        return ticket;
    }

}

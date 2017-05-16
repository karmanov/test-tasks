package io.karmanov.watermark;


import io.karmanov.watermark.domain.DocumentTopic;
import io.karmanov.watermark.domain.DocumentType;
import io.karmanov.watermark.domain.TicketStatus;
import io.karmanov.watermark.dto.DocumentDTO;
import io.karmanov.watermark.dto.TicketDTO;

import java.util.UUID;

public class StubDataBuilder {


    public static DocumentDTO buildDocument(Long id, String author, DocumentType type, String title, DocumentTopic topic) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setAuthor(author);
        documentDTO.setType(type);
        documentDTO.setTitle(title);
        documentDTO.setTopic(topic);
        documentDTO.setId(id);
        return documentDTO;
    }

    public static TicketDTO buildTicketDTO(Long documentId, DocumentDTO documentDTO, TicketStatus status) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setStatus(status);
        ticketDTO.setDocument(documentDTO);
        ticketDTO.setDocumentId(documentId);
        ticketDTO.setId(UUID.randomUUID());
        return ticketDTO;
    }
}

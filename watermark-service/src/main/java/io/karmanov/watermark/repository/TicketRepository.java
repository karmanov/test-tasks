package io.karmanov.watermark.repository;

import io.karmanov.watermark.domain.Ticket;
import io.karmanov.watermark.domain.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Optional<Ticket> findById(UUID id);

    Optional<Ticket> findOneByDocumentIdAndStatus(Long documentId, TicketStatus status);
}

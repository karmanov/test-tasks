package io.karmanov.watermark.domain;

import io.karmanov.watermark.converter.TicketStatusConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tickets")
public class Ticket {

    @Id
    private UUID id;

    private Long documentId;

    @Convert(converter = TicketStatusConverter.class)
    private TicketStatus status;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private DateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private DateTime modifiedDate;
}

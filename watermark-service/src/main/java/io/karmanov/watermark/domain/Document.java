package io.karmanov.watermark.domain;

import io.karmanov.watermark.converter.DocumentTopicConverter;
import io.karmanov.watermark.converter.DocumentTypeConverter;
import io.karmanov.watermark.converter.TicketStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String watermark;

    @Convert(converter = DocumentTopicConverter.class)
    private DocumentTopic topic;

    @Convert(converter = DocumentTypeConverter.class)
    private DocumentType type;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private DateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private DateTime modifiedDate;

}

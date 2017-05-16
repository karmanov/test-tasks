package io.karmanov.watermark.converter;

import io.karmanov.watermark.domain.Document;
import io.karmanov.watermark.domain.DocumentTopic;
import io.karmanov.watermark.dto.DocumentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for {@link Document} entity to {@link DocumentDTO} transformation
 */
@Component
public class DocumentDomainToDTOConverter implements Converter<Document, DocumentDTO> {

    @Override
    public DocumentDTO convert(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Could not convert null to DocumentDTO");
        }

        DocumentDTO documentDTO = new DocumentDTO();
        BeanUtils.copyProperties(document, documentDTO);
        return documentDTO;
    }
}

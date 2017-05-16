package io.karmanov.watermark.converter;

import io.karmanov.watermark.domain.Document;
import io.karmanov.watermark.dto.DocumentCreateRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for {@link DocumentCreateRequestDTO} entity to {@link Document} transformation
 */
@Component
public class DocumentDTOToDomainConverter implements Converter<DocumentCreateRequestDTO, Document> {

    @Override
    public Document convert(DocumentCreateRequestDTO requestDTO) {
        Document document = new Document();
        BeanUtils.copyProperties(requestDTO, document);
        return document;
    }
}

package io.karmanov.watermark.converter;

import io.karmanov.watermark.domain.DocumentTopic;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter for {@link DocumentTopic} enum value to {@link Integer} code
 */
@Converter
public class DocumentTopicConverter implements AttributeConverter<DocumentTopic, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DocumentTopic documentTopic) {
        if (documentTopic == null) {
            return null;
        }
        return documentTopic.getCode();
    }

    @Override
    public DocumentTopic convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return DocumentTopic.valueOf(dbData);
    }
}

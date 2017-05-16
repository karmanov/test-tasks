package io.karmanov.watermark.converter;

import io.karmanov.watermark.domain.DocumentType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.validation.constraints.NotNull;

/**
 * Converter for {@link DocumentType} enum value to {@link Integer} code
 */
@Converter
public class DocumentTypeConverter implements AttributeConverter<DocumentType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(@NotNull DocumentType documentType) {
        return documentType.getCode();
    }

    @Override
    public DocumentType convertToEntityAttribute(Integer dbData) {
        return DocumentType.valueOf(dbData);
    }
}

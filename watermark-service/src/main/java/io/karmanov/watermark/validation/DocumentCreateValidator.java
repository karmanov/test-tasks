package io.karmanov.watermark.validation;

import io.karmanov.watermark.domain.DocumentTopic;
import io.karmanov.watermark.domain.DocumentType;
import io.karmanov.watermark.dto.DocumentCreateRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Provides validation of {@link DocumentCreateRequestDTO} for document creation request
 */
@Component
public class DocumentCreateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DocumentCreateRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Title should be not null or empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "Author should be not null or empty");

        DocumentCreateRequestDTO request = (DocumentCreateRequestDTO) o;
        validateTopic(errors, request);
        validateType(errors, request);
    }

    private void validateTopic(Errors errors, DocumentCreateRequestDTO request) {
        DocumentTopic topic = request.getTopic();
        if (DocumentType.BOOK.equals(request.getType()) && topic == null) {
            errors.reject("topic", "Topic value is not exist");
        }
    }

    private void validateType(Errors errors, DocumentCreateRequestDTO request) {
        DocumentType type = request.getType();
        if (type == null) {
            errors.reject("type", "Type value is not exist");
        }
    }
}

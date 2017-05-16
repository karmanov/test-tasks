package io.karmanov.watermark.validation;

import io.karmanov.watermark.dto.WatermarkRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Provides validation of {@link io.karmanov.watermark.dto.WatermarkRequestDTO}
 * for watermarking the document request
 */

@Component
public class WatermarkRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return WatermarkRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "documentId", "Document id should not be null");
    }
}

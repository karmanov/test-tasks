package io.karmanov.watermark.service.processor;

import io.karmanov.watermark.domain.DocumentType;
import io.karmanov.watermark.dto.DocumentDTO;

public interface WatermarkProcessor {

    String generateWatermark(DocumentDTO document);

    DocumentType getSupportedType();

    default void simulateLongProcessing(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

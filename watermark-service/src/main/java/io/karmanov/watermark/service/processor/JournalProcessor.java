package io.karmanov.watermark.service.processor;

import io.karmanov.watermark.domain.DocumentType;
import io.karmanov.watermark.dto.DocumentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JournalProcessor implements WatermarkProcessor {

    @Value("${watermark.simulation.journal.delay:10000}")
    private int delay;

    @Override
    public String generateWatermark(DocumentDTO document) {
        simulateLongProcessing(delay);
        String title = document.getTitle();
        String content = document.getType().toString();
        String author = document.getAuthor();
        return "{content: '" + content + "', title: '" + title + "', author: '" + author + "'}";
    }

    @Override
    public DocumentType getSupportedType() {
        return DocumentType.JOURNAL;
    }
}

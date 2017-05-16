package io.karmanov.watermark.service.processor;

import io.karmanov.watermark.domain.DocumentType;
import io.karmanov.watermark.dto.DocumentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static io.karmanov.watermark.domain.DocumentType.BOOK;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookProcessor implements WatermarkProcessor {

    @Value("${watermark.simulation.book.delay:10000}")
    private long delay;

    @Override
    public String generateWatermark(DocumentDTO documentDTO) {
        simulateLongProcessing(delay);
        String title = documentDTO.getTitle();
        String content = documentDTO.getType().toString();
        String topic = documentDTO.getTopic().toString();
        String author = documentDTO.getAuthor();
        return "{content: '" + content + "', title: '" + title + "', author: '" + author + "', topic: '" + topic + "'}";
    }

    @Override
    public DocumentType getSupportedType() {
        return BOOK;
    }
}

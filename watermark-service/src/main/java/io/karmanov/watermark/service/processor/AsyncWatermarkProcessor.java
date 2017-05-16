package io.karmanov.watermark.service.processor;

import io.karmanov.watermark.domain.DocumentType;
import io.karmanov.watermark.dto.DocumentDTO;
import io.karmanov.watermark.dto.TicketDTO;
import io.karmanov.watermark.service.DocumentService;
import io.karmanov.watermark.service.TicketService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static io.karmanov.watermark.domain.TicketStatus.COMPLETE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class AsyncWatermarkProcessor {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private TicketService ticketService;

    @Setter
    @Autowired
    private List<WatermarkProcessor> processors;

    private Map<DocumentType, WatermarkProcessor> processorMap;


    @PostConstruct
    public void init() {
        processorMap = processors.stream()
                                 .collect(toMap(WatermarkProcessor::getSupportedType, identity()));
    }

    @Async("threadPoolTaskExecutor")
    public void watermarkDocument(DocumentDTO document, TicketDTO ticketDTO) {
        WatermarkProcessor processor = processorMap.get(document.getType());
        if (processor == null) {
            log.error("No watermark processor found for document: {}", document);
            throw new IllegalArgumentException("No watermark processor found for document");
        }
        String watermark = processor.generateWatermark(document);
        document.setWatermark(watermark);
        documentService.update(document);

        ticketDTO.setStatus(COMPLETE);
        ticketService.update(ticketDTO);
    }
}

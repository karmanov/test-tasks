package io.karmanov.watermark.service;

import io.karmanov.watermark.domain.Document;
import io.karmanov.watermark.domain.DocumentTopic;
import io.karmanov.watermark.domain.DocumentType;
import io.karmanov.watermark.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile({"development", "demo"})
public class SampleDataLoader {

    private final DocumentRepository documentRepository;

    @PostConstruct
    public void init() {
        log.info("Loading sample documents to the db");

        Document d1 = Document.builder()
                              .author("Bruce Wayne")
                              .type(DocumentType.BOOK)
                              .title("The Dark Code")
                              .topic(DocumentTopic.SCIENCE)
                              .build();

        Document d2 = Document.builder()
                              .author("Dr.Evil")
                              .type(DocumentType.BOOK)
                              .title("How to make money")
                              .topic(DocumentTopic.BUSINESS)
                              .build();

        Document d3 = Document.builder()
                              .author("Clark Kent")
                              .type(DocumentType.JOURNAL)
                              .title("Journal of human flight routes")
                              .build();

        documentRepository.save(Arrays.asList(d1, d2, d3));
        documentRepository.flush();
    }
}

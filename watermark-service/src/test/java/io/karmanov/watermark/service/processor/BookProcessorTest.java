package io.karmanov.watermark.service.processor;

import io.karmanov.watermark.dto.DocumentDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static io.karmanov.watermark.StubDataBuilder.buildDocument;
import static io.karmanov.watermark.domain.DocumentTopic.SCIENCE;
import static io.karmanov.watermark.domain.DocumentType.BOOK;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BookProcessorTest {

    private BookProcessor bookProcessor;

    @Before
    public void setUp() throws Exception {
        bookProcessor = new BookProcessor();
    }

    @Test
    public void generateWatermark() throws Exception {
        DocumentDTO documentDTO = buildDocument(1L, "Bruce Wayne", BOOK, "The Dark Code", SCIENCE);
        String watermark = bookProcessor.generateWatermark(documentDTO);
        assertEquals(watermark, "{content: 'BOOK', title: 'The Dark Code', author: 'Bruce Wayne', topic: 'SCIENCE'}");
    }
}

package io.karmanov.watermark.service.processor;

import io.karmanov.watermark.dto.DocumentDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static io.karmanov.watermark.StubDataBuilder.buildDocument;
import static io.karmanov.watermark.domain.DocumentType.JOURNAL;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class JournalProcessorTest {

    private JournalProcessor journalProcessor;

    @Before
    public void setUp() throws Exception {
        journalProcessor = new JournalProcessor();
    }

    @Test
    public void generateWatermark() throws Exception {
        DocumentDTO documentDTO = buildDocument(1L, "Bruce Wayne", JOURNAL, "The Dark Code", null);
        String watermark = journalProcessor.generateWatermark(documentDTO);
        assertEquals(watermark, "{content: 'JOURNAL', title: 'The Dark Code', author: 'Bruce Wayne'}");
    }

}

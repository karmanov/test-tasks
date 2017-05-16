package io.karmanov.watermark.service.processor;

import io.karmanov.watermark.dto.DocumentDTO;
import io.karmanov.watermark.dto.TicketDTO;
import io.karmanov.watermark.service.DocumentService;
import io.karmanov.watermark.service.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static io.karmanov.watermark.StubDataBuilder.buildDocument;
import static io.karmanov.watermark.StubDataBuilder.buildTicketDTO;
import static io.karmanov.watermark.domain.DocumentTopic.SCIENCE;
import static io.karmanov.watermark.domain.DocumentType.BOOK;
import static io.karmanov.watermark.domain.DocumentType.JOURNAL;
import static io.karmanov.watermark.domain.TicketStatus.IN_PROGRESS;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AsyncWatermarkProcessorTest {

    @Mock
    private BookProcessor bookProcessor;

    @Mock
    private JournalProcessor journalProcessor;

    @Mock
    private DocumentService documentService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private AsyncWatermarkProcessor asyncWatermarkProcessor;

    @Before
    public void setUp() throws Exception {
        when(bookProcessor.getSupportedType()).thenReturn(BOOK);
        when(journalProcessor.getSupportedType()).thenReturn(JOURNAL);

        List<WatermarkProcessor> processors = Arrays.asList(bookProcessor, journalProcessor);
        asyncWatermarkProcessor.setProcessors(processors);
        asyncWatermarkProcessor.init();
    }

    @Test
    public void watermarkBook() throws Exception {
        Long documentId = 1L;
        DocumentDTO documentDTO = buildDocument(1L, "Bruce Wayne", BOOK, "The Dark Code", SCIENCE);
        TicketDTO ticketDTO = buildTicketDTO(documentId, null, IN_PROGRESS);
        asyncWatermarkProcessor.watermarkDocument(documentDTO, ticketDTO);
        when(documentService.update(any(DocumentDTO.class))).thenReturn(documentDTO);
        when(ticketService.update(any(TicketDTO.class))).thenReturn(ticketDTO);
        verify(bookProcessor, times(1)).generateWatermark(documentDTO);
        verify(journalProcessor, times(0)).generateWatermark(documentDTO);
    }

    @Test
    public void watermarkJournal() throws Exception {
        Long documentId = 1L;
        DocumentDTO documentDTO = buildDocument(1L, "Bruce Wayne", JOURNAL, "The Dark Code", null);
        TicketDTO ticketDTO = buildTicketDTO(documentId, null, IN_PROGRESS);
        asyncWatermarkProcessor.watermarkDocument(documentDTO, ticketDTO);
        verify(bookProcessor, times(0)).generateWatermark(documentDTO);
        verify(journalProcessor, times(1)).generateWatermark(documentDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void watermarkIllegalType() throws Exception {
        Long documentId = 1L;
        DocumentDTO documentDTO = buildDocument(1L, "Bruce Wayne", null, "The Dark Code", null);
        TicketDTO ticketDTO = buildTicketDTO(documentId, null, IN_PROGRESS);
        asyncWatermarkProcessor.watermarkDocument(documentDTO, ticketDTO);
    }

}

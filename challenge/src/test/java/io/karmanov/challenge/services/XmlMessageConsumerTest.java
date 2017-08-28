package io.karmanov.challenge.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.karmanov.challenge.dto.MessageConsumingResult;
import io.karmanov.challenge.services.client.MessageServiceClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.karmanov.challenge.TestDataProvider.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class XmlMessageConsumerTest {

    @Mock
    private MessageServiceClient messageServiceClient;

    private XmlMessageConsumer messageConsumer;


    @Before
    public void setUp() throws Exception {
        messageConsumer = new XmlMessageConsumer(messageServiceClient);
    }

    @Test
    public void normalFlow() throws Exception {
        ResponseEntity<String> r1 = new ResponseEntity<>(XML_NORMAL_RECORD, HttpStatus.OK);
        ResponseEntity<String> r2 = new ResponseEntity<>(XML_DONE_RECORD, HttpStatus.OK);

        when(messageServiceClient.consumeXmlMessage()).thenReturn(XML_NORMAL_RECORD, XML_DONE_RECORD);

        MessageConsumingResult result = messageConsumer.consume();
        assertEquals(1, result.getMessages().size());
        assertTrue(result.isAllMessagesConsumed());
    }

    @Test
    public void badFormattedMessageFlow() throws Exception {
        ResponseEntity<String> r1 = new ResponseEntity<>(XML_NORMAL_RECORD, HttpStatus.OK);
        ResponseEntity<String> r2 = new ResponseEntity<>(XML_BAD_FORMATTED_RECORD, HttpStatus.OK);
        ResponseEntity<String> r3 = new ResponseEntity<>(XML_DONE_RECORD, HttpStatus.OK);

        when(messageServiceClient.consumeXmlMessage()).thenReturn(XML_NORMAL_RECORD, XML_BAD_FORMATTED_RECORD, XML_DONE_RECORD);

        MessageConsumingResult result = messageConsumer.consume();
        assertEquals(1, result.getMessages().size());
        assertTrue(result.isAllMessagesConsumed());
    }
}
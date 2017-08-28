package io.karmanov.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonMessageConsumerTest {

    @Mock
    private MessageServiceClient messageServiceClient;

    private JsonMessageConsumer messageConsumer;


    @Before
    public void setUp() throws Exception {
        messageConsumer = new JsonMessageConsumer(messageServiceClient);
    }

    @Test
    public void normalFlow() throws Exception {
        when(messageServiceClient.consumeJsonMessage()).thenReturn(JSON_NORMAL_RECORD, JSON_DONE_RECORD);

        MessageConsumingResult result = messageConsumer.consume();
        assertEquals(1, result.getMessages().size());
        assertTrue(result.isAllMessagesConsumed());
    }

    @Test
    public void badFormattedMessageFlow() throws Exception {
        ResponseEntity<String> r1 = new ResponseEntity<>(JSON_NORMAL_RECORD, HttpStatus.OK);
        ResponseEntity<String> r2 = new ResponseEntity<>(JSON_BAD_FORMATTED_RECORD, HttpStatus.OK);
        ResponseEntity<String> r3 = new ResponseEntity<>(JSON_DONE_RECORD, HttpStatus.OK);

        when(messageServiceClient.consumeJsonMessage()).thenReturn(JSON_NORMAL_RECORD, JSON_BAD_FORMATTED_RECORD, JSON_DONE_RECORD);

        MessageConsumingResult result = messageConsumer.consume();
        assertEquals(1, result.getMessages().size());
        assertTrue(result.isAllMessagesConsumed());
    }

}
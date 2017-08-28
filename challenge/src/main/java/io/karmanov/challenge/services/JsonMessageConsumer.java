package io.karmanov.challenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import io.karmanov.challenge.dto.JsonMessage;
import io.karmanov.challenge.dto.Message;
import io.karmanov.challenge.dto.MessageConsumingResult;
import io.karmanov.challenge.services.client.MessageServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonMessageConsumer implements MessageConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageServiceClient messageServiceClient;

    @Override
    public MessageConsumingResult consume() {
        List<Message> messages = new ArrayList<>();
        boolean allMessagesConsumed = false;

        while (!allMessagesConsumed) {
            try {
                String payload = messageServiceClient.consumeJsonMessage();
                log.info("Received message {}", payload);
                try {
                    JsonMessage message = objectMapper.readValue(payload, JsonMessage.class);
                    if ("done".equals(message.getStatus())) {
                        allMessagesConsumed = true;
                    } else {
                        messages.add(message);
                    }
                } catch (IOException e) {
                    log.error("Oops, could not deserialize message {}. Reason: {}", payload, e.getMessage());
                }
            } catch (FeignException e) {
                if (HttpStatus.NOT_ACCEPTABLE.value() == e.status()) {
                    log.warn("code 406, message you gotta read or write somewhere else first");
                    break;
                }
            }

        }

        return new MessageConsumingResult(messages, allMessagesConsumed);
    }
}

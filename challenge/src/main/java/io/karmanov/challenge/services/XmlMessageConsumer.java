package io.karmanov.challenge.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.FeignException;
import io.karmanov.challenge.dto.Message;
import io.karmanov.challenge.dto.MessageConsumingResult;
import io.karmanov.challenge.dto.XmlMessage;
import io.karmanov.challenge.services.client.MessageServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class XmlMessageConsumer implements MessageConsumer {

    private final XmlMapper xmlMapper = new XmlMapper();
    private final MessageServiceClient messageServiceClient;

    @Override
    public MessageConsumingResult consume() {
        List<Message> messages = new ArrayList<>();
        boolean allMessagesConsumed = false;

        while (!allMessagesConsumed) {
            try {
                String payload = messageServiceClient.consumeXmlMessage();
                log.info("Received message {}", payload);
                try {
                    XmlMessage message = xmlMapper.readValue(payload, XmlMessage.class);
                    if (message.getId() == null && message.getDoneStatus() == null) {
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

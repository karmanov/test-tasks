package io.karmanov.challenge.services;

import io.karmanov.challenge.dto.Message;
import io.karmanov.challenge.dto.MessageConsumingResult;
import io.karmanov.challenge.dto.SinkRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.util.Collections.frequency;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProcessorService {

    private final XmlMessageConsumer xmlMessageConsumer;
    private final JsonMessageConsumer jsonMessageConsumer;
    private final MessageProducerService messageProducerService;

    private boolean isAllJsonMessagesConsumed;
    private boolean isAllXmlMessagesConsumed;
    List<String> allMessages = new ArrayList<>();


    public void processMessages() {
        log.info("Processing messages started");

        while (!isAllJsonMessagesConsumed && !isAllXmlMessagesConsumed) {

            if (!isAllJsonMessagesConsumed) {
                MessageConsumingResult jsonMessageConsumingResult = jsonMessageConsumer.consume();
                isAllJsonMessagesConsumed = jsonMessageConsumingResult.isAllMessagesConsumed();
                allMessages.addAll(convertMessages(jsonMessageConsumingResult.getMessages()));
            }

            if (!isAllXmlMessagesConsumed) {
                MessageConsumingResult xmlMessageConsumingResult = xmlMessageConsumer.consume();
                isAllXmlMessagesConsumed = xmlMessageConsumingResult.isAllMessagesConsumed();
                allMessages.addAll(convertMessages(xmlMessageConsumingResult.getMessages()));
            }

            sendResults();

        }

        log.info("Is all messages was send: {}", messageProducerService.isQueueEmpty());
        log.info("Processing messages done");
    }


    private List<String> convertMessages(List<Message> messages) {
        return messages.stream().map(Message::getHash).collect(toList());
    }

    private void sendResults() {
        List<String> joinedMessages = findJoinedMessages();
        List<String> orphanedMessages = findOrphaned(joinedMessages);
        allMessages.clear();

        List<SinkRequest> allRequests = new ArrayList<>();
        allRequests.addAll(joinedMessages.stream().map(m -> buildRequest("joined", m)).collect(toList()));
        allRequests.addAll(orphanedMessages.stream().map(m -> buildRequest("orphaned", m)).collect(toList()));
        messageProducerService.produceResults(allRequests);
    }

    private List<String> findJoinedMessages() {
        return allMessages.stream()
                          .filter(h -> frequency(allMessages, h) > 1)
                          .collect(toList());
    }

    private List<String> findOrphaned(List<String> joinedMessages) {
        ArrayList<String> copy = new ArrayList<>(allMessages);
        copy.removeAll(joinedMessages);
        return copy;
    }

    private SinkRequest buildRequest(String kind, String id) {
        return new SinkRequest(kind, id);
    }


}

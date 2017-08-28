package io.karmanov.challenge.services;

import feign.FeignException;
import io.karmanov.challenge.dto.SinkRequest;
import io.karmanov.challenge.dto.SinkResponse;
import io.karmanov.challenge.services.client.MessageServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService {

    private Queue<SinkRequest> messageQueue = new LinkedList<>();

    private final MessageServiceClient messageServiceClient;

    public void produceResults(List<SinkRequest> requests) {
        messageQueue.addAll(requests);


        while (!messageQueue.isEmpty()) {
            try {
                SinkRequest request = messageQueue.remove();
                log.info("Posting result request {}", request);
                SinkResponse sinkResponse = messageServiceClient.postSingleResult(request);
                log.info("Response {}", sinkResponse);
            } catch (FeignException e) {
                if (HttpStatus.NOT_ACCEPTABLE.value() == e.status()) {
                    log.warn("code 406, message you gotta read or write somewhere else first");
                    break;
                }
            }
        }
    }

    public boolean isQueueEmpty() {
        return messageQueue.isEmpty();
    }

}

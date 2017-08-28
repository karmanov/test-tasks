package io.karmanov.challenge.services.client;

import io.karmanov.challenge.dto.SinkRequest;
import io.karmanov.challenge.dto.SinkResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@FeignClient(url = "http://localhost:7299", name = "messageProducer")
public interface MessageServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/source/a",
            consumes = APPLICATION_JSON_VALUE)
    String consumeJsonMessage();

    @RequestMapping(method = RequestMethod.GET, value = "/source/b",
            consumes = APPLICATION_XML_VALUE)
    String consumeXmlMessage();

    @RequestMapping(method = RequestMethod.POST, value = "/sink/a",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    SinkResponse postSingleResult(@RequestBody SinkRequest request);
}

package com.challenge.service;

import com.challenge.exception.UrlFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.jsoup.Jsoup.connect;

@Slf4j
@Component
public class UrlDocumentFetcher implements DocumentFetcher {

    private static final String USER_AGENT = "Mozilla";

    @Override
    public Document fetch(String resource) {
        try {
            log.debug("Retrieving document from url {}", resource);
            validateUrl(resource);
            return connect(resource).userAgent(USER_AGENT).timeout(3000).get();
        } catch (IOException e) {
            log.error("Error occurred during fetching information form url {}. Reason: {}", resource, e.getMessage());
            throw new UrlFormatException(resource);
        }
    }

    private void validateUrl(String resource) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        boolean isValidUrl = urlValidator.isValid(resource);
        if (!isValidUrl) {
            throw new UrlFormatException(resource);
        }
    }
}

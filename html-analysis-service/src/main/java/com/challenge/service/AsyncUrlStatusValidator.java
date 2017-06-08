package com.challenge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Future;

@Slf4j
@Service
public class AsyncUrlStatusValidator {

    @Value("${challenge.url.connection.timeout:3000}")
    private int timeout;

    @Async("threadPoolTaskExecutor")
    public Future<Integer> validateUrl(String url) {
        log.debug("Validating status of url {}", url);
        try {
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setInstanceFollowRedirects(false);
            huc.setConnectTimeout(timeout);
            huc.setReadTimeout(timeout);
            huc.setRequestMethod("HEAD");
            huc.connect();
            InputStream errorStream = huc.getErrorStream();
            return new AsyncResult<>(huc.getResponseCode());
        } catch (IOException e) {
            log.error("Error occurred during checking the status of url {}. Reason: {}", url, e.getMessage());
            return new AsyncResult<>(-1);
        }
    }
}

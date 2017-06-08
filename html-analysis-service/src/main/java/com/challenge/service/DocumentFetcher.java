package com.challenge.service;

import org.jsoup.nodes.Document;

public interface DocumentFetcher {

    Document fetch(String resource);
}

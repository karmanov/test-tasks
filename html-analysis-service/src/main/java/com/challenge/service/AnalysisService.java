package com.challenge.service;

import com.challenge.dto.LinkResourceDTO;
import com.challenge.dto.LinkResourceType;
import com.challenge.dto.PageAnalysisReportDTO;
import com.challenge.dto.PageAnalysisRequestDTO;
import com.google.common.net.InternetDomainName;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.challenge.dto.LinkResourceType.EXTERNAL;
import static com.challenge.dto.LinkResourceType.INTERNAL;
import static java.util.Collections.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Slf4j
@Service
@AllArgsConstructor
public class AnalysisService {

    private static final String PUBLICID = "publicid";
    private static final String HEADING_CSS_QUERY = "h1, h2, h3, h4, h5, h6";
    private static final String PASSWORD = "password";
    private static final String HTML_5_VERSION = "html5";
    private static final String HTML_4_VERSION = "html4";
    private static final String TYPE = "type";
    private static final String EMPTY = "";
    private static final String A_HREF = "a[href]";
    private static final String ABS_HREF = "abs:href";

    private AsyncUrlStatusValidator urlStatusValidator;

    private DocumentFetcher documentFetcher;

    public PageAnalysisReportDTO analyzePage(PageAnalysisRequestDTO request) {
        log.info("Analysing page {}", request.getUrl());
        Document document = documentFetcher.fetch(request.getUrl());
        String htmlVersion = getHtmlVersion(document);
        Map<String, Long> headingsMap = getHeadingsMap(document);
        boolean hasLoginForm = hasLoginForm(document);
        Map<Integer, List<LinkResourceDTO>> linksMap = getLinks(document).stream()
                                                                         .collect(groupingBy(LinkResourceDTO::getType));

        return PageAnalysisReportDTO.builder()
                                    .url(request.getUrl())
                                    .title(document.title())
                                    .htmlVersion(htmlVersion)
                                    .headingsMap(headingsMap)
                                    .containsLoginForm(hasLoginForm)
                                    .linksMap(linksMap)
                                    .headingCount(countHeadings(headingsMap))
                                    .internalLinksCount(countLinks(INTERNAL, linksMap))
                                    .externalLinksCount(countLinks(EXTERNAL, linksMap))
                                    .build();
    }

    private int countHeadings(Map<String, Long> headingsMap) {
        return headingsMap.values()
                          .stream()
                          .mapToInt(Number::intValue)
                          .sum();
    }

    private int countLinks(LinkResourceType type, Map<Integer, List<LinkResourceDTO>> linksMap) {
        return linksMap.getOrDefault(type.getCode(), emptyList()).size();
    }

    private List<LinkResourceDTO> getLinks(Document document) {
        String domainName = getRootDomain(document.location());
        Set<String> allLinks = getAllLink(document);
        Map<String, Future<Integer>> resultsMap = new HashMap<>();
        List<LinkResourceDTO> result = new ArrayList<>();
        allLinks.forEach(l -> {
            Future<Integer> statusCode = urlStatusValidator.validateUrl(l);
            resultsMap.put(l, statusCode);
        });

        for (Map.Entry<String, Future<Integer>> entry : resultsMap.entrySet()) {
            try {
                String url = entry.getKey();
                Integer statusCode = entry.getValue().get();
                boolean onSameDomain = isOnSameDomain(domainName, url);
                LinkResourceType type = onSameDomain ? INTERNAL : EXTERNAL;
                LinkResourceDTO linkResourceDTO = new LinkResourceDTO(url, statusCode, type.getCode());
                result.add(linkResourceDTO);
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error occurred during validation URL {} ", entry.getKey());
            }
        }
        return result;
    }

    private Set<String> getAllLink(Document document) {
        Set<String> links = document.select(A_HREF).stream()
                                    .map(e -> e.attr(ABS_HREF))
                                    .collect(toSet());
        log.debug("Found {} links on the page", links.size());
        return links;
    }

    private boolean isOnSameDomain(String domainName, String url) {
        String urlDomainName = getRootDomain(url);
        return domainName.equals(urlDomainName);
    }

    private Map<String, Long> getHeadingsMap(Document document) {
        log.debug("Building headings map");
        Map<String, Long> result = document.select(HEADING_CSS_QUERY).stream()
                                           .map(Element::tagName)
                                           .collect(groupingBy(identity(), counting()));
        log.trace("Built {} map of heading tags", result);
        return result;
    }

    private String getHtmlVersion(Document document) {
        log.debug("Checking HTML version");
        return document.childNodes()
                       .stream()
                       .filter(node -> node instanceof DocumentType)
                       .map(this::generateHtmlVersion)
                       .collect(joining());
    }

    private String generateHtmlVersion(Node node) {
        DocumentType documentType = (DocumentType) node;
        String htmlVersion = documentType.attr(PUBLICID);
        return EMPTY.equals(htmlVersion) ? HTML_5_VERSION : HTML_4_VERSION;
    }

    private boolean hasLoginForm(Document document) {
        log.debug("Checking is login form exist on the page");
        Elements passwordInputElements = document.getElementsByAttributeValue(TYPE, PASSWORD);
        log.trace("Found {} password input elements on the page", passwordInputElements.size());
        return !passwordInputElements.isEmpty();
    }

    private String getRootDomain(String url) {
        log.debug("Get root domain from url {}", url);
        if (url == null || url.isEmpty()) {
            return EMPTY;
        }

        try {
            URL u = new URL(url);
            String host = u.getHost();
            return InternetDomainName.from(host).topPrivateDomain().toString();
        } catch (MalformedURLException | IllegalArgumentException e) {
            log.error("Error occurred during getting doamin name for URL: {}", url);
            return EMPTY;
        }
    }
}

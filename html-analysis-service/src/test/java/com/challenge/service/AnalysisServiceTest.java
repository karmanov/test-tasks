package com.challenge.service;

import com.challenge.dto.PageAnalysisReportDTO;
import com.challenge.dto.PageAnalysisRequestDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class AnalysisServiceTest {

    @Mock
    private AsyncUrlStatusValidator urlStatusValidator;

    @Mock
    private DocumentFetcher documentFetcher;

    @InjectMocks
    private AnalysisService analysisService;

    @Test
    public void analyze_page_without_links() throws Exception {
        String url = "http://example.com";
        Document document = buildMockDocument("data/test-general.html", url);
        when(documentFetcher.fetch(url)).thenReturn(document);

        PageAnalysisReportDTO reportDTO = analysisService.analyzePage(buildRequest(url));
        assertEquals(url, reportDTO.getUrl());
        assertEquals("html5", reportDTO.getHtmlVersion());
        assertEquals("Test 01 title", reportDTO.getTitle());
        assertTrue(reportDTO.getHeadingsMap().containsKey("h1"));
        assertTrue(reportDTO.getHeadingsMap().containsKey("h2"));
        assertTrue(reportDTO.getHeadingsMap().containsKey("h3"));
        assertTrue(reportDTO.getHeadingsMap().containsKey("h4"));
        assertTrue(reportDTO.getHeadingsMap().containsKey("h5"));
        assertTrue(reportDTO.getHeadingsMap().containsKey("h6"));
        assertEquals(1L, reportDTO.getHeadingsMap().get("h1").longValue());
        assertEquals(2L, reportDTO.getHeadingsMap().get("h2").longValue());
        assertEquals(3L, reportDTO.getHeadingsMap().get("h3").longValue());
        assertEquals(4L, reportDTO.getHeadingsMap().get("h4").longValue());
        assertEquals(5L, reportDTO.getHeadingsMap().get("h5").longValue());
        assertEquals(6L, reportDTO.getHeadingsMap().get("h6").longValue());
        assertEquals(21, reportDTO.getHeadingCount());
        assertTrue(reportDTO.getLinksMap().isEmpty());
        assertEquals(0, reportDTO.getExternalLinksCount());
        assertEquals(0, reportDTO.getInternalLinksCount());
        assertFalse(reportDTO.isContainsLoginForm());
    }

    @Test
    public void analyze_page_with_loginForm() throws Exception {
        String url = "http://example.com";
        Document document = buildMockDocument("data/test-login-form.html", url);
        when(documentFetcher.fetch(url)).thenReturn(document);

        PageAnalysisReportDTO reportDTO = analysisService.analyzePage(buildRequest(url));
        assertTrue(reportDTO.isContainsLoginForm());
    }

    @Test
    public void analyze_page_with_html4_version() throws Exception {
        String url = "http://example.com";
        Document document = buildMockDocument("data/test-login-form.html", url);
        when(documentFetcher.fetch(url)).thenReturn(document);

        PageAnalysisReportDTO reportDTO = analysisService.analyzePage(buildRequest(url));
        assertEquals("html4", reportDTO.getHtmlVersion());
    }

    @Test
    public void analyze_page_with_links() throws Exception {
        String url = "https://www.reddit.com/";
        Document document = buildMockDocument("data/test-links.html", url);

        when(documentFetcher.fetch(url)).thenReturn(document);
        when(urlStatusValidator.validateUrl("https://www.reddit.com")).thenReturn(new AsyncResult<>(OK.value()));
        when(urlStatusValidator.validateUrl("https://www.reddit.com/r/aww/comments/69x9z3/cuddles_the_hand/")).thenReturn(new AsyncResult<>(OK.value()));
        when(urlStatusValidator.validateUrl("https://sub1.reddit.com")).thenReturn(new AsyncResult<>(NOT_FOUND.value()));
        when(urlStatusValidator.validateUrl("http://spring.io/")).thenReturn(new AsyncResult<>(OK.value()));
        when(urlStatusValidator.validateUrl("http://spring.io/ads")).thenReturn(new AsyncResult<>(NOT_FOUND.value()));
        when(urlStatusValidator.validateUrl("http://example.com/")).thenReturn(new AsyncResult<>(OK.value()));
        when(urlStatusValidator.validateUrl("http://sample.com/")).thenReturn(new AsyncResult<>(OK.value()));
        when(urlStatusValidator.validateUrl("http://@$Q@%123")).thenReturn(new AsyncResult<>(-1));

        PageAnalysisReportDTO reportDTO = analysisService.analyzePage(buildRequest(url));
        assertEquals(3, reportDTO.getInternalLinksCount());
        assertEquals(5, reportDTO.getExternalLinksCount());
    }


    private PageAnalysisRequestDTO buildRequest(String url) {
        return new PageAnalysisRequestDTO(url);
    }

    private Document buildMockDocument(String fileName, String url) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File input = new File(classLoader.getResource(fileName).getFile());
        return Jsoup.parse(input, "UTF-8", url);
    }

}
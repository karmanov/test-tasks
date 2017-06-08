package com.challenge.controller;

import com.challenge.dto.PageAnalysisReportDTO;
import com.challenge.dto.PageAnalysisRequestDTO;
import com.challenge.service.AnalysisService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class AnalysisController {

    private AnalysisService analysisService;

    @PostMapping(value = "/analyze", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public PageAnalysisReportDTO analyzePage(@Validated @RequestBody PageAnalysisRequestDTO request) {
        return analysisService.analyzePage(request);
    }
}

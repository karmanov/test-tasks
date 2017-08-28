package io.karmanov.tms.controller;

import io.karmanov.tms.domain.Statistics;
import io.karmanov.tms.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/statistics")
@Api(description = "Provides operation for retrieving transactions statistics for last 60 seconds")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Provide operation for retrieving transactions statistics for last 60 seconds")
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }
}

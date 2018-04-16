package io.temperaturestats.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.temperaturestats.dto.MeasurementCreateRequestDTO;
import io.temperaturestats.dto.MeasurementDTO;
import io.temperaturestats.dto.SensorEventsResponseDTO;
import io.temperaturestats.dto.SensorStatisticsDTO;
import io.temperaturestats.service.EventService;
import io.temperaturestats.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/temperature-statistics/api/v1/sensors/{uuid}")
@Api(description = "Provides API to submit sensor's measurement, get events and statistics")
public class TemperatureController {

    private final MeasurementService measurementService;

    private final EventService eventService;

    @ApiOperation("Submit sensor's measurement")
    @PostMapping(value = "/measurement", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MeasurementDTO submitMeasurements(@PathVariable("uuid") UUID sensorId, @RequestBody MeasurementCreateRequestDTO measurementCreateRequestDTO) {
        return measurementService.submitMeasurements(sensorId, measurementCreateRequestDTO);
    }

    @ApiOperation("Get sensor's events by sensor id")
    @GetMapping(value = "/events", produces = APPLICATION_JSON_VALUE)
    public SensorEventsResponseDTO getEventsBySensor(@PathVariable("uuid") UUID sensorId) {
        return eventService.getEventsBySensor(sensorId);
    }

    @ApiOperation("Get sensor's statistics by sensor id")
    @GetMapping(value = "/statistics", produces = APPLICATION_JSON_VALUE)
    public SensorStatisticsDTO getStatisticsBySensor(@PathVariable("uuid") UUID sensorId) {
        return measurementService.getSensorStatistics(sensorId);
    }

}

package io.temperaturestats.service;

import io.temperaturestats.converter.ConverterService;
import io.temperaturestats.domain.Measurement;
import io.temperaturestats.dto.MeasurementCreateRequestDTO;
import io.temperaturestats.dto.MeasurementDTO;
import io.temperaturestats.dto.SensorStatisticsDTO;
import io.temperaturestats.repository.MeasurementRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.temperaturestats.domain.EventType.TEMPERATURE_EXCEEDED;
import static io.temperaturestats.utils.CollectionOperations.getAverage;
import static io.temperaturestats.utils.CollectionOperations.getMax;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final ConverterService converterService;

    private final EventService eventService;

    @Value("${temperature-statistics.sensor-threshold:95.0}")
    private Double sensorThreshold;

    @Value("${temperature-statistics.hit-count:3}")
    private int countOfHitTheThreshold;

    public MeasurementDTO submitMeasurements(UUID sensorId, MeasurementCreateRequestDTO measurementCreateRequestDTO) {
        log.info("Saving measurements for sensor {}, temperature: {}", sensorId, measurementCreateRequestDTO.getTemperature());
        Measurement measurement = measurementRepository.saveAndFlush(buildMeasurement(sensorId, measurementCreateRequestDTO));
        if (isEventShouldBeTriggered(sensorId, measurement.getTemperature())) {
            eventService.createEvent(sensorId, measurement.getTemperature(), TEMPERATURE_EXCEEDED);
            List<Measurement> measurements = measurementRepository.findAllBySensorId(sensorId);
            measurements.forEach(m -> m.setValidated(true));
            measurementRepository.save(measurements);
        }
        return convert(measurement);
    }

    public SensorStatisticsDTO getSensorStatistics(@NonNull UUID sensorId) {
        log.debug("Fetching statistics for sensor {}");
        Double averageLastHour = getAverage(findBySensorIdLastHour(sensorId));
        Double averageLastSevenDays = getAverage(findBySensorIdLastSevenDays(sensorId));
        Double maxLast30Days = getMax(findBySensorIdLast30Days(sensorId));
        return SensorStatisticsDTO.builder()
                                  .sensorUuid(sensorId)
                                  .averageLastHour(averageLastHour)
                                  .averageLast7Days(averageLastSevenDays)
                                  .maxLast30Days(maxLast30Days)
                                  .build();
    }

    private boolean isEventShouldBeTriggered(UUID sensorId, Double temperature) {
        if (temperature >= sensorThreshold) {
            List<Measurement> historyMeasurements = measurementRepository.findFirst3BySensorIdAndValidatedFalseOrderByAtDesc(sensorId);
            if (historyMeasurements.size() > (countOfHitTheThreshold - 1)) {
                return historyMeasurements.stream().allMatch( m -> m.getTemperature() >= sensorThreshold);
            }
        }
        return false;
    }

    private Measurement buildMeasurement(UUID sensorId, MeasurementCreateRequestDTO measurementCreateRequestDTO) {
        Measurement measurement = convert(measurementCreateRequestDTO);
        measurement.setSensorId(sensorId);
        measurement.setValidated(false);
        return measurement;
    }

    private List<Measurement> findBySensorIdLastHour(UUID sensorId) {
        DateTime dateTime = DateTime.now().minusHours(1);
        return findBySensorIdAndAtGreaterThanEqual(sensorId, dateTime);
    }

    private List<Measurement> findBySensorIdLastSevenDays(UUID sensorId) {
        DateTime dateTime = DateTime.now().minusDays(7);
        return findBySensorIdAndAtGreaterThanEqual(sensorId, dateTime);
    }

    private List<Measurement> findBySensorIdLast30Days(UUID sensorId) {
        DateTime dateTime = DateTime.now().minusDays(30);
        return findBySensorIdAndAtGreaterThanEqual(sensorId, dateTime);
    }

    private List<Measurement> findBySensorIdAndAtGreaterThanEqual(UUID sensorId, DateTime at) {
        return measurementRepository.findBySensorIdAndAtGreaterThanEqual(sensorId, at);
    }

    private Measurement convert(MeasurementCreateRequestDTO measurementCreateRequestDTO) {
        return converterService.convert(measurementCreateRequestDTO, Measurement.class);
    }

    private MeasurementDTO convert(Measurement measurement) {
        return converterService.convert(measurement, MeasurementDTO.class);
    }
}

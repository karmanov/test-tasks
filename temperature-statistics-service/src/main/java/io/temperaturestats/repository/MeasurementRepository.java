package io.temperaturestats.repository;

import io.temperaturestats.domain.Measurement;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> findAllBySensorId(UUID sensorId);

    List<Measurement> findBySensorIdAndAtGreaterThanEqual(UUID sensorId, DateTime at);

    List<Measurement> findFirst3BySensorIdAndValidatedFalseOrderByAtDesc(UUID sensorId);
}

package io.temperaturestats.repository;

import io.temperaturestats.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findBySensorId(UUID sensorId);
}

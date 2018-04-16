package io.temperaturestats.service;

import io.temperaturestats.converter.ConverterService;
import io.temperaturestats.domain.Event;
import io.temperaturestats.domain.EventType;
import io.temperaturestats.dto.EventDTO;
import io.temperaturestats.dto.SensorEventsResponseDTO;
import io.temperaturestats.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final ConverterService converterService;

    public void createEvent(UUID sensorId, Double temperature, EventType type) {
        log.info("Creating new event type {} for sensor {} with temperature {}", type, sensorId, temperature);
        Event event = Event.builder()
                           .sensorId(sensorId)
                           .temperature(temperature)
                           .type(type)
                           .at(DateTime.now())
                           .build();

        eventRepository.saveAndFlush(event);
    }

    public SensorEventsResponseDTO getEventsBySensor(UUID sensorId) {
        log.debug("Fetching events for sensor id {}", sensorId);
        List<EventDTO> events = eventRepository.findBySensorId(sensorId).stream()
                                               .map(this::convert)
                                               .collect(toList());
        return new SensorEventsResponseDTO(sensorId, events);
    }

    private EventDTO convert(Event event) {
        return converterService.convert(event, EventDTO.class);
    }

}

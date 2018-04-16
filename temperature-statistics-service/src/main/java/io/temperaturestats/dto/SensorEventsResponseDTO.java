package io.temperaturestats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorEventsResponseDTO {

    private UUID sensorUuid;

    private List<EventDTO> events;
}

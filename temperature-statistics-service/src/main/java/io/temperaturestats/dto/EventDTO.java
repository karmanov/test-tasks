package io.temperaturestats.dto;

import io.temperaturestats.domain.EventType;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class EventDTO {

    private EventType type;

    private DateTime at;

    private Double temperature;
}

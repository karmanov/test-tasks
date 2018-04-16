package io.temperaturestats.converter;

import io.temperaturestats.domain.Event;
import io.temperaturestats.dto.EventDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventEntityToDTOConverter implements Converter<Event, EventDTO> {

    @Override
    public EventDTO convert(Event event) {
        EventDTO eventDTO = new EventDTO();
        BeanUtils.copyProperties(event, eventDTO);
        return eventDTO;
    }
}

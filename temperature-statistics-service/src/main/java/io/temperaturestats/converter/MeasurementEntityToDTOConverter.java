package io.temperaturestats.converter;

import io.temperaturestats.domain.Measurement;
import io.temperaturestats.dto.MeasurementDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MeasurementEntityToDTOConverter implements Converter<Measurement, MeasurementDTO> {

    @Override
    public MeasurementDTO convert(Measurement measurement) {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        BeanUtils.copyProperties(measurement, measurementDTO);
        return measurementDTO;
    }
}

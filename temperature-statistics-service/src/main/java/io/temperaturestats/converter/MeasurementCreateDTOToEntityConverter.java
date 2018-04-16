package io.temperaturestats.converter;

import io.temperaturestats.domain.Measurement;
import io.temperaturestats.dto.MeasurementCreateRequestDTO;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MeasurementCreateDTOToEntityConverter implements Converter<MeasurementCreateRequestDTO, Measurement> {

    @Override
    public Measurement convert(MeasurementCreateRequestDTO measurementCreateRequestDTO) {
        Measurement measurement = new Measurement();
        BeanUtils.copyProperties(measurementCreateRequestDTO, measurement);
        measurement.setAt(DateTime.now());
        return measurement;
    }
}

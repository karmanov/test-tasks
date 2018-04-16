package io.temperaturestats;

import io.temperaturestats.domain.Measurement;
import io.temperaturestats.dto.MeasurementCreateRequestDTO;

public class TestDataProvider {

    public static Measurement buildMeasurement(double temperature) {
        Measurement measurement = new Measurement();
        measurement.setTemperature(temperature);
        return measurement;
    }

    public static MeasurementCreateRequestDTO buildMeasurementCreateRequestDTO(Double temperature) {
        return new MeasurementCreateRequestDTO(temperature);
    }
}

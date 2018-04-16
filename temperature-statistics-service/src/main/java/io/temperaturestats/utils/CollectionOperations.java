package io.temperaturestats.utils;

import io.temperaturestats.domain.Measurement;

import java.util.List;

public class CollectionOperations {

    public static Double getAverage(List<Measurement> measurements) {
        return measurements.stream()
                           .mapToDouble(Measurement::getTemperature)
                           .average().orElse(0);
    }

    public static Double getMax(List<Measurement> measurements) {
        return measurements.stream()
                           .mapToDouble(Measurement::getTemperature)
                           .max().orElse(0);
    }
}

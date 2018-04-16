package io.temperaturestats.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class SensorStatisticsDTO {

    private UUID sensorUuid;

    private Double averageLastHour;

    private Double averageLast7Days;

    private Double maxLast30Days;
}

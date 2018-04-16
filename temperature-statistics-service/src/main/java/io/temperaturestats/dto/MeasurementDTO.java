package io.temperaturestats.dto;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.UUID;

@Data
public class MeasurementDTO {

    private UUID id;

    private UUID sensorId;

    private Double temperature;

    private DateTime at;
}

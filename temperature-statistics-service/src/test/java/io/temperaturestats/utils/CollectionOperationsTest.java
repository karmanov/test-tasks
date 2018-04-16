package io.temperaturestats.utils;

import io.temperaturestats.domain.Measurement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static io.temperaturestats.TestDataProvider.buildMeasurement;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CollectionOperationsTest {

    List<Measurement> measurements;

    @Before
    public void setUp() {
        measurements = Arrays.asList(
                buildMeasurement(10.1),
                buildMeasurement(13.2),
                buildMeasurement(16.3)
        );
    }

    @Test
    public void calculateAverage() {
        Double average = CollectionOperations.getAverage(measurements);
        assertEquals(average, 13.2, 0.001);
    }

    @Test
    public void calculateMax() {
        Double max = CollectionOperations.getMax(measurements);
        assertEquals(max, 16.3, 0.001);
    }

    @Test
    public void calculateMaxEmptyList() {
        Double max = CollectionOperations.getMax(emptyList());
        assertEquals(max, 0, 0.001);
    }

    @Test
    public void calculateAvgEmptyList() {
        Double avg = CollectionOperations.getAverage(emptyList());
        assertEquals(avg, 0, 0.001);
    }
}

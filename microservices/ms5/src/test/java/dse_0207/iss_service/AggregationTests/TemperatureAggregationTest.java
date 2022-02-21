package dse_0207.iss_service.AggregationTests;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.EventAggregation.EventAggregation;
import dse_0207.iss_service.EventAggregation.TemperatureAggregation;
import dse_0207.iss_service.EventSimulation.EventSimulation;
import dse_0207.iss_service.EventSimulation.TemperatureSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TemperatureAggregationTest {
    List<Event> testEvents;
    EventAggregation temperatureAggregation;
    EventSimulation temperatureSimulation;

    public final int PERIOD = 10;

    @BeforeEach
    void setUp() {
        temperatureAggregation = new TemperatureAggregation();
        temperatureSimulation = new TemperatureSimulation();
        testEvents = new ArrayList<>();

        for (int i = 0; i < PERIOD; ++i) {
            testEvents.add(temperatureSimulation.simulate());
        }
    }

    @Test
    void aggregation_shouldNotDeliverNull() {
        Event testObject = temperatureAggregation.aggregate(testEvents);

        assertNotNull(testObject);
    }
}

package dse_0207.iss_service.AggregationTests;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.EventAggregation.EventAggregation;
import dse_0207.iss_service.EventAggregation.WeatherAggregation;
import dse_0207.iss_service.EventSimulation.EventSimulation;
import dse_0207.iss_service.EventSimulation.WeatherSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WeatherAggregationTest {
    List<Event> testEvents;
    EventAggregation weatherAggregation;
    EventSimulation weatherSimulation;

    public final int PERIOD = 10;

    @BeforeEach
    void setUp() {
        weatherAggregation = new WeatherAggregation();
        weatherSimulation = new WeatherSimulation();
        testEvents = new ArrayList<>();

        for (int i = 0; i < PERIOD; ++i) {
            testEvents.add(weatherSimulation.simulate());
        }
    }

    @Test
    void aggregation_shouldNotDeliverNull() {
        Event testObject = weatherAggregation.aggregate(testEvents);

        assertNotNull(testObject);
    }
}

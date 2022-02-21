package dse_0207.iss_service.SimulationTests;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.EventSimulation.EventSimulation;
import dse_0207.iss_service.EventSimulation.WeatherSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WeatherSimulationTest {
    EventSimulation weatherSimulation;

    @BeforeEach
    void setUp() {
        weatherSimulation = new WeatherSimulation();
    }

    @Test
    void simulationShouldNotDeliverNull() {
        Event testObject = weatherSimulation.simulate();

        assertNotNull(testObject);
    }
}

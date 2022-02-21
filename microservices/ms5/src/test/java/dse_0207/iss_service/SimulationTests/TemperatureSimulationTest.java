package dse_0207.iss_service.SimulationTests;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.EventSimulation.EventSimulation;
import dse_0207.iss_service.EventSimulation.TemperatureSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TemperatureSimulationTest {
    EventSimulation temperatureSimulation;

    @BeforeEach
    void setUp() {
        temperatureSimulation = new TemperatureSimulation();
    }

    @Test
    void simulationShouldNotDeliverNull() {
        Event testObject = temperatureSimulation.simulate();

        assertNotNull(testObject);
    }
}

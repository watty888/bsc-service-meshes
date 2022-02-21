package dse_0207.iss_service.EventSimulation;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.Event.TemperatureEvent;

import java.util.concurrent.ThreadLocalRandom;

public class TemperatureSimulation implements EventSimulation {
  @Override
  public Event simulate() {
    double temperatureRecord = ThreadLocalRandom.current().nextDouble(15, 40 + 1);

    return new TemperatureEvent(temperatureRecord);
  }
}

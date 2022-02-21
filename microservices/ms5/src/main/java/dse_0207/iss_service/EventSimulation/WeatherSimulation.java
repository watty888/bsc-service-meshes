package dse_0207.iss_service.EventSimulation;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.Event.WeatherEvent;

import java.util.Random;

public class WeatherSimulation implements EventSimulation {
  @Override
  public Event simulate() {
    Random rn = new Random();

    int weatherRecord = rn.nextInt(3) + 1;

    switch (weatherRecord) {
      case 1:
        return new WeatherEvent(EWeather.sunny);
      case 2:
        return new WeatherEvent(EWeather.rainy);
      case 3:
        return new WeatherEvent(EWeather.cloudy);
      default:
        throw new IllegalStateException("Unexpected value: " + weatherRecord);
    }
  }
}

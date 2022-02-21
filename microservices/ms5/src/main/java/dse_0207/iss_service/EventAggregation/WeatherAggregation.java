package dse_0207.iss_service.EventAggregation;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.Event.WeatherEvent;
import dse_0207.iss_service.EventSimulation.EWeather;

import java.util.List;

/**
 *
 */
public class WeatherAggregation implements EventAggregation {

  @Override
  public Event aggregate(List<Event> rawData) {
    int sunnyCounter = 0;
    int rainyCounter = 0;
    int cloudyCounter = 0;
    int mostOften = 0;
    EWeather result = null;

    for (Event rawDatum : rawData) {
      switch (((WeatherEvent) rawDatum).getWeather()) {
        case sunny:
          ++sunnyCounter;

          if (sunnyCounter > mostOften) {
            mostOften = sunnyCounter;
            result = EWeather.sunny;
          }

          break;
        case rainy:
          ++rainyCounter;

          if (rainyCounter > mostOften) {
            mostOften = rainyCounter;
            result = EWeather.rainy;
          }

          break;
        case cloudy:
          ++cloudyCounter;

          if (cloudyCounter > mostOften) {
            mostOften = cloudyCounter;
            result = EWeather.cloudy;
          }

          break;
      }
    }

    return new WeatherEvent(result);
  }

}

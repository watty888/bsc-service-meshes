package dse_0207.iss_service.Event;

import dse_0207.iss_service.EventSimulation.EWeather;
import dse_0207.shared_components.Message.ETopic;

public class WeatherEvent extends Event {
  private EWeather weather;

  public WeatherEvent(EWeather weather) {
    setWeather(weather);
    setBody(weather);
  }

  public EWeather getWeather() {
    return weather;
  }

  private void setWeather(EWeather temperature) {
    this.weather = temperature;
  }

  @Override
  public ETopic getTopic() {
    return ETopic.WEATHER_OBSERVATION;
  }
}

package dse_0207.iss_service.Event;

import dse_0207.iss_service.EventSimulation.EWeather;
import dse_0207.shared_components.Message.ETopic;

public abstract class Event {
  protected String body;

  protected void setBody(EWeather weather) {
    this.body = weather.name();
  }

  protected void setBody(double temperature) {
    this.body = String.valueOf(temperature);
  }

  public String getBody() {
    return body;
  }

  public abstract ETopic getTopic();
}

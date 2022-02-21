package dse_0207.iss_service.Event;

import dse_0207.shared_components.Message.ETopic;

public class TemperatureEvent extends Event {
  private double temperature;

  public TemperatureEvent(double temperature) {
    setTemperature(temperature);

    setBody(temperature);
  }

  public double getTemperature() {
    return temperature;
  }

  private void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  @Override
  public ETopic getTopic() {
    return ETopic.TEMPERATURE_OBSERVATION;
  }
}

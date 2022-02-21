package dse_0207.shared_components.Message;

public enum ETopic {
  predictions("predictions"),
  anomalies("anomalies"),
  subscribers("subscribers"),
  WEATHER_OBSERVATION("weather_observation"),
  TEMPERATURE_OBSERVATION("temperature_observation"),
  WEATHER_PREDICTION("weather_prediction"),
  TEMPERATURE_PREDICTION("temperature_prediction"),
  WEATHER_ANOMALY("weather_anomaly"),
  TEMPERATURE_ANOMALY("temperature_anomaly");


  public final String label;

  ETopic(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}

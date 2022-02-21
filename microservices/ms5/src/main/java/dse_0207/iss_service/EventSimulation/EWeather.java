package dse_0207.iss_service.EventSimulation;

public enum EWeather {
  sunny(1),
  rainy(2),
  cloudy(3);

  public final int index;

  private EWeather(int index) {
    this.index = index;
  }
}

package dse_0207.iss_service.EventAggregation.Aggregators;

import dse_0207.iss_service.EventAggregation.EventAggregation;

public class WeatherAggregator extends Aggregator {

  public WeatherAggregator(EventAggregation aggregation) {
    super(aggregation);

    this.addPropertyChangeListener(this);
  }
}

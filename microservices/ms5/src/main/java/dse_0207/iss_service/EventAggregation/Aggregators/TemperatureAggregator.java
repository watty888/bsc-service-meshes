package dse_0207.iss_service.EventAggregation.Aggregators;

import dse_0207.iss_service.EventAggregation.EventAggregation;

public class TemperatureAggregator extends Aggregator {

  public TemperatureAggregator(EventAggregation aggregation) {
    super(aggregation);

    this.addPropertyChangeListener(this);
  }

}

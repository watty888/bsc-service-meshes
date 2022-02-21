package dse_0207.iss_service.EventAggregation;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.Event.TemperatureEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TemperatureAggregation implements EventAggregation {

  public Event aggregate(List<Event> rawData) {
    double averageTemperature = 0;
    double tempAccumulator = 0.;

    Event aggregatedEvent = null;

    for (int i = 0; i < rawData.size(); ++i) {
      TemperatureEvent e = (TemperatureEvent) rawData.get(i);

      tempAccumulator += e.getTemperature();

      if ((i + 1) % 10 == 0) {
        BigDecimal bd1 =
            new BigDecimal(Double.toString(tempAccumulator / 10.0))
                .setScale(2, RoundingMode.HALF_UP);
        averageTemperature += bd1.doubleValue();

        aggregatedEvent = new TemperatureEvent(averageTemperature);

        averageTemperature = 0.;
        tempAccumulator = 0.;
      }
    }

    return aggregatedEvent;
  }
}

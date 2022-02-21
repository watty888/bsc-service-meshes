package dse_0207.iss_service.EventSimulation.Simulators;

import dse_0207.iss_service.Event.Event;
import dse_0207.iss_service.EventAggregation.Aggregators.Aggregator;
import dse_0207.iss_service.EventAggregation.Aggregators.ConcurrentAggregator;
import dse_0207.iss_service.EventAggregation.Aggregators.TemperatureAggregator;
import dse_0207.iss_service.EventAggregation.Aggregators.WeatherAggregator;
import dse_0207.iss_service.EventAggregation.TemperatureAggregation;
import dse_0207.iss_service.EventAggregation.WeatherAggregation;
import dse_0207.iss_service.EventSimulation.EventSimulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we define the main simulation logic.
 * Depending on the chosen strategy the Simulator will bee able to either perform weather or temperature simulations
 */
public abstract class Simulator {
  protected EventSimulation simulation;
  protected List<Event> rawObservations;
  private Aggregator aggregator;

  Simulator(EventSimulation simulation) {
    this.rawObservations = new ArrayList<>();

    this.setSimulation(simulation);
    this.createAggregator();
  }

  public void performSimulations(long endTime, int nThreads) throws InterruptedException {
    while (endTime >= System.currentTimeMillis()) {
      Thread.sleep(700);
      Event e = this.simulation.simulate();
      addRawObservation(e);

      for (int i = 0; i < nThreads; ++i) {
        ConcurrentAggregator ca = new ConcurrentAggregator(aggregator, getRawObservations());
        Thread t = new Thread(ca);
        t.start();
      }

      this.rawObservations = new ArrayList<>();

    }

  }

  private void createAggregator() {
    if (this instanceof WeatherSimulator) {
      this.setAggregator(new WeatherAggregator(new WeatherAggregation()));
    } else if (this instanceof TemperatureSimulator) {
      this.setAggregator(new TemperatureAggregator(new TemperatureAggregation()));
    }
  }

  private List<Event> getRawObservations() {
    return rawObservations;
  }

  private void addRawObservation(Event observation) {
    this.rawObservations.add(observation);
  }

  public void setAggregator(Aggregator aggregator) {
    this.aggregator = aggregator;
  }

  private void setSimulation(EventSimulation simulation) {
    this.simulation = simulation;
  }
}

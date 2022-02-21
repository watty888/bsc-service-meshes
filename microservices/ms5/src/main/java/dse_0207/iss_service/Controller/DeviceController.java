package dse_0207.iss_service.Controller;

import dse_0207.iss_service.EventSimulation.Simulators.ESimulatorType;
import dse_0207.iss_service.EventSimulation.Simulators.Simulator;
import dse_0207.iss_service.EventSimulation.Simulators.TemperatureSimulator;
import dse_0207.iss_service.EventSimulation.Simulators.WeatherSimulator;

/**
 * Device bootstrapper class
 */
public class DeviceController {
  private Simulator simulator;

  /**
   * Type decides which of 2 possible strategies to initiate
   *
   * @param type
   */
  public void instantiateDevice(ESimulatorType type) {
    switch (type) {
      case weather:
        this.simulator = new WeatherSimulator();
        break;
      case temperature:
        this.simulator = new TemperatureSimulator();
        break;
    }
  }

  public void startDevice(long endTime, int nThreads) throws InterruptedException {
    this.simulator.performSimulations(endTime, nThreads);
  }
}

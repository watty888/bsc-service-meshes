package dse_0207.machine_learning_microservice.machine_learning_anomaly.anomaly_model;

import static org.junit.Assert.*;

import dse_0207.machine_learning_microservice.machine_learning_anomaly.Anomaly;
import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ChainAnomalyDetectionModelTest {
  @Parameters(name = "{index}: test ({0}) against ({1}) should return ({2})")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { "sunny", "cloudy", false},
        { "cloudy", "sunny", false},
        { "cloudy", "rainy", false},
        { "rainy", "cloudy", false},

        { "sunny", "rainy", true},
        { "rainy", "sunny", true},

        { "sunny", "sunny", false},
    });
  }

  private Message previousTestMessage;
  private Message testMessage;
  private boolean expectedHasAnomalyValue;

  public ChainAnomalyDetectionModelTest(String previousTestValue, String testValue, boolean expectedHasAnomalyValue) {
    this.previousTestMessage = new Message("test-id", ETopic.WEATHER_OBSERVATION, "test-url", EPriority.HIGH, previousTestValue);
    this.testMessage = new Message("test-id", ETopic.WEATHER_OBSERVATION, "test-url", EPriority.HIGH, testValue);
    this.expectedHasAnomalyValue = expectedHasAnomalyValue;
  }

  @Test
  public void getAnomaly() {
    ChainAnomalyDetectionModel model = new ChainAnomalyDetectionModel();

    Anomaly anomaly = model.getAnomaly(this.previousTestMessage, this.testMessage);
    assertEquals(this.expectedHasAnomalyValue, anomaly.hasAnomaly());
  }
}
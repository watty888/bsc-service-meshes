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

// https://github.com/junit-team/junit4/wiki/Parameterized-tests
@RunWith(Parameterized.class)
public class RangeAnomalyDetectionModelTest {
  @Parameters(name = "{index}: test ({0}) against ({1}) should return ({2})")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { "33", "44", true},
        { "33", "20", true},

        { "33", "35", false},
        { "33", "30", false},

        { "33", "43", false},
        { "33", "23", false},

        { "33", "33", false},
    });
  }

  private Message previousTestMessage;
  private Message testMessage;
  private boolean expectedHasAnomalyValue;

  public RangeAnomalyDetectionModelTest(String previousTestValue, String testValue, boolean expectedHasAnomalyValue) {
    this.previousTestMessage = new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.HIGH, previousTestValue);
    this.testMessage = new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.HIGH, testValue);
    this.expectedHasAnomalyValue = expectedHasAnomalyValue;
  }

  @Test
  public void TheAnomalyShouldBeTrueIfTheRangeChangesMoreThan10() {
    RangeAnomalyDetectionModel model = new RangeAnomalyDetectionModel();
    Anomaly anomaly = model.getAnomaly(this.previousTestMessage, this.testMessage);

    assertEquals(anomaly.hasAnomaly(), this.expectedHasAnomalyValue);
  }
}
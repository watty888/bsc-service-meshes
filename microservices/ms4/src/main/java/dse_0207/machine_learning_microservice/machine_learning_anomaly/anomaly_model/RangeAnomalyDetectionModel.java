package dse_0207.machine_learning_microservice.machine_learning_anomaly.anomaly_model;

import dse_0207.machine_learning_microservice.machine_learning_anomaly.Anomaly;
import dse_0207.shared_components.Message.Message;

public class RangeAnomalyDetectionModel extends AnomalyDetectionModel {
  private static int RANGE = 10;
  @Override
  public Anomaly getAnomaly(Message previousMessage, Message message) {
    double previousMessageValue = Double.valueOf(previousMessage.getBody());
    double messageValue = Double.valueOf(message.getBody());

    boolean hasAnomaly = false;
    if (messageValue > previousMessageValue) {
      hasAnomaly = (previousMessageValue + RANGE) < messageValue;
    } else if (messageValue < previousMessageValue) {
      hasAnomaly = (previousMessageValue - RANGE) > messageValue;
    }

    String anomalyMessage = "";
    if (hasAnomaly) {
      anomalyMessage += messageValue + " does not fit the into the specified range of " + RANGE + ". The previous value was " + previousMessageValue;
    } else {
      anomalyMessage += "No anomaly";
    }

    return new Anomaly(hasAnomaly, anomalyMessage, message, super.getAnomalyTopicLabelFromObservationTopic(message.getTopic()));
  }
}

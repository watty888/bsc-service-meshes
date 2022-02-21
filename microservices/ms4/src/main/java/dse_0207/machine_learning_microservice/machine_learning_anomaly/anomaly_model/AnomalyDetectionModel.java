package dse_0207.machine_learning_microservice.machine_learning_anomaly.anomaly_model;

import dse_0207.machine_learning_microservice.machine_learning_anomaly.Anomaly;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.HashMap;
import java.util.Map;

public abstract class AnomalyDetectionModel {
  public abstract Anomaly getAnomaly(Message previousMessage, Message message);

  public static ETopic getAnomalyTopicLabelFromObservationTopic(ETopic topic) {
    Map<ETopic, ETopic> topicMappings = new HashMap<>();
    topicMappings.put(ETopic.WEATHER_OBSERVATION, ETopic.WEATHER_ANOMALY);
    topicMappings.put(ETopic.TEMPERATURE_OBSERVATION, ETopic.TEMPERATURE_ANOMALY);

    ETopic topicMapping = topicMappings.get(topic);

    if (topicMapping == null) {
      throw new RuntimeException("Couldn't get anomaly topic from observation topic");
    }

    return topicMapping;
  }
}

package dse_0207.machine_learning_microservice.machine_learning_anomaly;

import dse_0207.machine_learning_microservice.MachineLearningMicroserviceApplication;
import dse_0207.machine_learning_microservice.machine_learning_anomaly.anomaly_model.AnomalyDetectionModel;
import dse_0207.machine_learning_microservice.machine_learning_anomaly.anomaly_model.ChainAnomalyDetectionModel;
import dse_0207.machine_learning_microservice.machine_learning_anomaly.anomaly_model.RangeAnomalyDetectionModel;
import dse_0207.shared_components.Environment;
import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AnomalyService {
  private static Logger logger =
      LoggerFactory.getLogger(MachineLearningMicroserviceApplication.class);
  private List<Anomaly> anomalies;

  public AnomalyService() {
    this.anomalies = new ArrayList<>();
  }

  public synchronized boolean calculateAndPublishAnomaly(List<Message> messageInput, ETopic topic) {
    RestTemplate restTemplate = new RestTemplate();

    List<Message> messages =
        messageInput.stream().filter(m -> m.getTopic().equals(topic)).collect(Collectors.toList());

    Anomaly anomaly = this.calculateAndSaveAnomaly(messages);

    try {
      restTemplate.postForLocation(
          Environment.MESSAGE_QUEUE_URL + "/api/v1/events", createMessageFromAnomaly(anomaly));

      logger.info("Anomaly " + anomaly.getMessage() + " successfully sent");
    } catch (Exception e) {
      logger.error("Unable to publish anomaly " + anomaly.getMessage() + " to the message queue");
    }

    return true;
  }

  private synchronized Anomaly calculateAndSaveAnomaly(List<Message> messages) {
    AnomalyDetectionModel model = null;

    int messagesCount = messages.size();
    if (messagesCount == 0) {
      throw new RuntimeException("Can't calculate anomaly without any message");
    }

    Message message = messages.get(messagesCount - 1);

    switch (message.getTopic()) {
      case TEMPERATURE_OBSERVATION:
        model = new RangeAnomalyDetectionModel();
        break;
      case WEATHER_OBSERVATION:
        model = new ChainAnomalyDetectionModel();
        break;
    }

    Anomaly anomaly = null;
    if (messagesCount == 1) {
      anomaly =
          new Anomaly(
              false,
              "No anomaly",
              messages.get(0),
              AnomalyDetectionModel.getAnomalyTopicLabelFromObservationTopic(
                  messages.get(0).getTopic()));
    } else {
      Message lastMessage = messages.get(messagesCount - 1);
      Message secondLastMessage = messages.get(messagesCount - 2);

      anomaly = model.getAnomaly(secondLastMessage, lastMessage);
    }

    this.anomalies.add(anomaly);

    return anomaly;
  }

  public synchronized List<Anomaly> getAnomalies(List<ETopic> topics, int limit, String order) {
    List<Anomaly> filteredAnomalies =
        this.anomalies.stream()
            .filter(anomaly -> topics.contains(anomaly.getReferenceMessage().getTopic()))
            .collect(Collectors.toList());

    if (order.equals("DESC")) {
      Collections.reverse(filteredAnomalies);
    }

    limit = filteredAnomalies.size() < limit ? filteredAnomalies.size() : limit;
    return filteredAnomalies.subList(0, limit);
  }

  private Message createMessageFromAnomaly(Anomaly anomaly) {
    return new Message(
        anomaly.getTopic(), Environment.MACHINE_LEARNING_URL, EPriority.LOW, anomaly.getMessage());
  }
}

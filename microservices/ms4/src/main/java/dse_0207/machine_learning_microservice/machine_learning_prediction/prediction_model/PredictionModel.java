package dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model;

import dse_0207.machine_learning_microservice.machine_learning_prediction.Prediction;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PredictionModel {
  public abstract  Prediction getPrediction(List<Message> messages, ETopic topic);

  public List<Message> getMessagesWithTopic(List<Message> messages, ETopic topic) {
    List<Message> filteredMessages = new ArrayList<>();

    for(Message message: messages) {
      if (message.getTopic().equals(topic)) {
        filteredMessages.add(message);
      }
    }

    return filteredMessages;
  }

  public ETopic getPredictionTopicLabelFromObservationTopic(ETopic topic) {
    Map<ETopic, ETopic> topicMappings = new HashMap<>();
    topicMappings.put(ETopic.WEATHER_OBSERVATION, ETopic.WEATHER_PREDICTION);
    topicMappings.put(ETopic.TEMPERATURE_OBSERVATION, ETopic.TEMPERATURE_PREDICTION);

    ETopic predictionTopic = topicMappings.get(topic);
    if (predictionTopic == null) {
      throw new RuntimeException("Couldn't convert topic to a predictionTopic");
    }

    return predictionTopic;
  }
}

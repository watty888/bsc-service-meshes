package dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model;

import dse_0207.machine_learning_microservice.machine_learning_prediction.Prediction;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;

public class AveragePredictionModel extends PredictionModel {
  @Override
  public Prediction getPrediction(List<Message> messages, ETopic topic) {
    List<Message> filteredMessages = super.getMessagesWithTopic(messages, topic);

    Double sumOfMessageValues = 0.0;
    for (Message message: filteredMessages) {
      double messageValue = Double.valueOf(message.getBody());
      sumOfMessageValues += messageValue;
    }

    double averageMessageValue = sumOfMessageValues/filteredMessages.size();
    return new Prediction(super.getPredictionTopicLabelFromObservationTopic(topic), String.valueOf(averageMessageValue));
  }
}

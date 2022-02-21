package dse_0207.machine_learning_microservice.machine_learning_anomaly.anomaly_model;

import dse_0207.machine_learning_microservice.machine_learning_anomaly.Anomaly;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChainAnomalyDetectionModel extends AnomalyDetectionModel {
  private Map<String, List<String>> possibleTransitions = new HashMap<>();

  public ChainAnomalyDetectionModel() {
    List<String> possibleTransitionsFromSunny = new ArrayList<>();
    possibleTransitionsFromSunny.add("cloudy");

    this.possibleTransitions.put("sunny", possibleTransitionsFromSunny);

    List<String> possibleTransitionsFromCloudy = new ArrayList<>();
    possibleTransitionsFromCloudy.add("sunny");
    possibleTransitionsFromCloudy.add("rainy");

    this.possibleTransitions.put("cloudy", possibleTransitionsFromCloudy);

    List<String> possibleTransitionsFromRaining = new ArrayList<>();
    possibleTransitionsFromRaining.add("cloudy");

    this.possibleTransitions.put("rainy", possibleTransitionsFromRaining);
  }

  @Override
  public Anomaly getAnomaly(Message previousMessage, Message message) {
    List<String> allowedTransitions = this.possibleTransitions.get(previousMessage.getBody());

    boolean hasAnomaly = true;
    if (previousMessage.getBody().equals(message.getBody())) {
      hasAnomaly = false;
    } else {
      hasAnomaly = !allowedTransitions.contains(message.getBody());
    }

    String expectedValueMessage = "";
    if (hasAnomaly) {
      expectedValueMessage += "Transition from " + previousMessage.getBody() + " to " + message.getBody() + " should not occur.";
    } else {
      expectedValueMessage += "No anomaly";
    }

    return new Anomaly(hasAnomaly, expectedValueMessage, message, super.getAnomalyTopicLabelFromObservationTopic(message.getTopic()));
  }
}

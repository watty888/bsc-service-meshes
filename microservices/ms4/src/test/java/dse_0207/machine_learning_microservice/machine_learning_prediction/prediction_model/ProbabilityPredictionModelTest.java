package dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model;

import static org.junit.Assert.*;

import dse_0207.machine_learning_microservice.machine_learning_prediction.Prediction;
import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ProbabilityPredictionModelTest {
  private static ETopic SAMPLE_TOPIC = ETopic.WEATHER_OBSERVATION;

  @org.junit.Test
  public void ItShouldPredictTheValueWithTheHighestOccurrence() {
    List<Message> sampleMessages = getSampleMessages();
    ProbabilityPredictionModel model = new ProbabilityPredictionModel();

    Prediction prediction = model.getPrediction(sampleMessages, SAMPLE_TOPIC);
    assertEquals(prediction.getBody(), "raining");
    assertEquals(prediction.getTopic(), ETopic.WEATHER_PREDICTION);
  }

  @Test
  public void ItShouldPredictTheLatestValueWhenHavingTheSameOccurrence() {
    List<Message> sampleMessages = getSampleMessages();
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC, "test.url", EPriority.HIGH, "sunny"));

    ProbabilityPredictionModel model = new ProbabilityPredictionModel();
    Prediction prediction = model.getPrediction(sampleMessages, SAMPLE_TOPIC);
    assertEquals(prediction.getBody(), "sunny");
  }

  private List<Message> getSampleMessages() {
    List<Message> sampleMessages = new ArrayList<>();
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC, "test.url", EPriority.HIGH, "raining"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC, "test.url", EPriority.HIGH, "raining"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC, "test.url", EPriority.HIGH, "raining"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC, "test.url", EPriority.HIGH, "cloudy"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC, "test.url", EPriority.HIGH, "sunny"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC, "test.url", EPriority.HIGH, "sunny"));

    return sampleMessages;
  }
}
package dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model;

import static org.junit.Assert.*;

import dse_0207.machine_learning_microservice.machine_learning_prediction.Prediction;
import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;

public class AveragePredictionModelTest {
  private static String AVERAGE_TEMPERATURE_OF_SAMPLES = "34.05";
  private static ETopic SAMPLE_TOPIC = ETopic.WEATHER_OBSERVATION;

  @org.junit.Test
  public void ItShouldReturnAPredictionBasedOnTheAverageValue() {
    List<Message> sampleMessages = getSampleMessages();
    AveragePredictionModel model = new AveragePredictionModel();

    Prediction prediction = model.getPrediction(sampleMessages, SAMPLE_TOPIC );

    assertEquals(prediction.getBody(), AVERAGE_TEMPERATURE_OF_SAMPLES);
    assertEquals(prediction.getTopic(), ETopic.WEATHER_PREDICTION);
  }

  private List<Message> getSampleMessages() {
    List<Message> sampleMessages = new ArrayList<>();
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC , "test.url", EPriority.HIGH, "32.3"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC , "test.url", EPriority.HIGH, "33.3"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC , "test.url", EPriority.HIGH, "39.3"));
    sampleMessages.add(new Message("testId1", SAMPLE_TOPIC , "test.url", EPriority.HIGH, "31.3"));

    return sampleMessages;
  }
}
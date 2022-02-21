package dse_0207.machine_learning_microservice.machine_learning_prediction;

import dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model.AveragePredictionModel;
import dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model.PredictionModel;
import dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model.ProbabilityPredictionModel;
import dse_0207.shared_components.Environment;
import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.*;
import org.springframework.web.client.RestTemplate;

public class PredictionService {
    private PredictionRepository predictionRepository;

    public PredictionService() {
        this.predictionRepository = new PredictionRepository();
    }

    public synchronized void publishPrediction(Prediction p) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation(Environment.MESSAGE_QUEUE_URL + "/api/v1/events", createMessageFromPrediction(p));
    }

    /**
     * Get the predictions
     * @param topics A list of topics to filter for
     * @return A list of strings containing the predictions
     */
    public synchronized List<Prediction> getPredictions(List<ETopic> topics, int limit, String order) {
        return this.predictionRepository.getPredictions(topics, limit, order);
    }

    /**
     * Calculates new predictions for a list of messages
     * @param messages The list of messages
     * @return The calculated prediction
     */
    public synchronized Prediction calculatePrediction(List<Message> messages, ETopic topic) {
        PredictionModel predictionModel = null;

        if (topic.equals(ETopic.TEMPERATURE_OBSERVATION)) {
            predictionModel = new AveragePredictionModel();
        } else if (topic.equals(ETopic.WEATHER_OBSERVATION)) {
            predictionModel = new ProbabilityPredictionModel();
        }

        Prediction prediction = predictionModel.getPrediction(messages, topic);
        this.savePrediction(prediction);

        return prediction;
    }

    private synchronized void savePrediction(Prediction prediction) {
        this.predictionRepository.savePrediction(prediction);
    }

    private Message createMessageFromPrediction(Prediction prediction) {
        return new Message(prediction.getTopic(), Environment.MACHINE_LEARNING_URL, EPriority.LOW, prediction.getBody());
    }
}

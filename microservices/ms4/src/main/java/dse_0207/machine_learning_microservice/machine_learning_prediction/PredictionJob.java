package dse_0207.machine_learning_microservice.machine_learning_prediction;

import dse_0207.shared_components.Message.Message;
import java.util.List;

public class PredictionJob implements Runnable {
    private PredictionService predictionService;
    private List<Message> messages;

    public PredictionJob(PredictionService predictionService, List<Message> messages) {
        this.predictionService = predictionService;
        this.messages = messages;
    }

    @Override
    public void run() {
        Message message = this.messages.get(this.messages.size() - 1);
        Prediction prediction = this.predictionService.calculatePrediction(this.messages, message.getTopic());
        this.predictionService.publishPrediction(prediction);
    }
}

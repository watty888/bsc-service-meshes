package dse_0207.machine_learning_microservice.machine_learning_anomaly;

import dse_0207.shared_components.Message.Message;
import java.util.List;

public class AnomalyJob implements Runnable {
    private AnomalyService anomalyService;
    private List<Message> messages;

    public AnomalyJob(AnomalyService anomalyService, List<Message> messages) {
        this.anomalyService = anomalyService;
        this.messages = messages;
    }

    @Override
    public void run() {
        Message message = this.messages.get(this.messages.size() - 1);
        this.anomalyService.calculateAndPublishAnomaly(this.messages, message.getTopic());
    }
}

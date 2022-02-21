package dse_0207.machine_learning_microservice.machine_learning_prediction;

import dse_0207.shared_components.Message.ETopic;
import java.util.Date;

public class Prediction {
    private ETopic topic;
    private String prediction;
    private Date createdAt;

    public Prediction(ETopic topic, String prediction) {
        this.topic =topic;
        this.prediction = prediction;
        this.createdAt = new Date();
    }

    public ETopic getTopic() {
        return topic;
    }

    public String getBody() {
        return prediction;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}

package dse_0207.machine_learning_microservice.machine_learning_anomaly;

import com.fasterxml.jackson.annotation.JsonProperty;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.Date;

public class Anomaly {
    private boolean anomalyStatus;
    private String message;
    private ETopic topic;
    private Message referenceMessage;
    private Date createdAt;

    public Anomaly(boolean hasAnomaly, String expectedValue, Message message, ETopic topic) {
        this.anomalyStatus = hasAnomaly;
        this.message = expectedValue;
        this.referenceMessage = message;
        this.createdAt = new Date();
        this.topic = topic;
    }

    public boolean hasAnomaly() {
        return anomalyStatus;
    }

    public String getMessage() {
        return message;
    }

    public Message getReferenceMessage() {
        return referenceMessage;
    }

    // https://stackoverflow.com/a/38638571
    @JsonProperty("has_anomaly")
    public boolean getAnomalyStatus() {
        return anomalyStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ETopic getTopic() {
        return topic;
    }
}

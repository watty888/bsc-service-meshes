package dse_0207.mqm_service;

import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;

public class Subscriber {
    private Integer id;
    private String url;
    private List<ETopic> topics;
    private List<String> receivedMessageIds = new ArrayList<>();

    public Subscriber() {}

    public Subscriber(Integer id, String url, List<ETopic> topics) {
        this.id = id;
        this.url = url;
        this.topics = topics;
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public List<ETopic> getTopics() {
        return topics;
    }

    public boolean isInterested(ETopic topic) {
        return this.topics.contains(topic);
    }

    public boolean hasReceivedMessage(Message m) {
        return this.receivedMessageIds.contains(m.getId());
    }

    public void markMessageAsReceived(Message m) {
        this.receivedMessageIds.add(m.getId());
    }
}

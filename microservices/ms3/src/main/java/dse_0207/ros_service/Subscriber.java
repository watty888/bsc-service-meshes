package dse_0207.ros_service;

import dse_0207.shared_components.Message.ETopic;

import java.util.List;

public class Subscriber {
    private Integer id;
    private String url;
    private List<ETopic> topics;
    public String action;


    public Subscriber() { }

    public Subscriber(Integer id, String url, List<ETopic> topics, String action) {
        this.id = id;
        this.url = url;
        this.topics = topics;
        this.action = action;
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
}

package dse_0207.iss_service.Rest;

import dse_0207.shared_components.Message.ETopic;

import java.util.List;

public class Subscriber {
  private Integer id;
  private String url;
  private List<ETopic> topics;

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
}

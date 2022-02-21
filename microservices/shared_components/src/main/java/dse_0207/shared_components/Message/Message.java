package dse_0207.shared_components.Message;

public class Message {
  private String id;
  private ETopic topic;
  private String url;
  private EPriority priority;
  private String body;

  public Message() {}

  public Message(String id, ETopic topics, String url, EPriority priority, String body) {
    this.id = id;
    this.topic = topics;
    this.url = url;
    this.priority = priority;
    this.body = body;
  }

  public Message(ETopic topic, String url, EPriority priority, String body) {
    // https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode()
    this.id = String.valueOf(hashCode());
    this.topic = topic;
    this.url = url;
    this.priority = priority;
    this.body = body;
  }

  public String getId() {
    return id;
  }

  public ETopic getTopic() {
    return topic;
  }

  public String getUrl() {
    return url;
  }

  public EPriority getPriority() {
    return priority;
  }

  public String getBody() {
    return body;
  }
}

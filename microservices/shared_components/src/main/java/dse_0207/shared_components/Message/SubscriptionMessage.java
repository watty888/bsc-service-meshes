package dse_0207.shared_components.Message;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;

public class SubscriptionMessage {
  // https://www.baeldung.com/spring-boot-bean-validation
  @NotEmpty(message = "Please provide an action")
  public String action;

  // https://www.baeldung.com/spring-boot-bean-validation
  @NotEmpty(message = "Please provide a list of topics to subscribe to")
  public List<ETopic> topics = new ArrayList<>();

  // https://www.baeldung.com/spring-boot-bean-validation
  @NotEmpty(message = "Please provide an URL for callback")
  public String url;

  public Integer id;

  public SubscriptionMessage() {}

  public SubscriptionMessage(String action,
      List<ETopic> topics, String url, Integer id) {
    this.action = action;
    this.topics = topics;
    this.url = url;

    /*
      This is only needed when you want to unsubscribe
      Otherwise you can pass in null
     */
    this.id = id;
  }
}

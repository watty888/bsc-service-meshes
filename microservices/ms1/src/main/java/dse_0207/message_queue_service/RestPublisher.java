package dse_0207.message_queue_service;

import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class RestPublisher {
  private static final Logger logger = LogManager.getLogger("Controller");

  public static boolean publish(List<Message> messages, List<Subscriber> subscribers) {
    for (Message message: messages) {
      ETopic messageTopic = message.getTopic();

      for (Subscriber subscriber: subscribers) {
        // https://www.baeldung.com/rest-template
        if (subscriber.isInterested(messageTopic) && !subscriber.hasReceivedMessage(message)) {
          try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Message> request = new HttpEntity<>(message);

            String url = subscriber.getUrl() + "/api/v1/events";
            restTemplate.postForLocation(url, request);

            subscriber.markMessageAsReceived(message);

            logger.info("Subscriber " + subscriber.getId() + " successfully fetched message");
          } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
          }
        }
      }
    }

    return true;
  }
}

package dse_0207.iss_service.Rest;

import dse_0207.iss_service.Event.Event;
import dse_0207.shared_components.Environment;
import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * A service for publishing gathered and aggregated observations
 */
public class RestPublisher {
  private final static Logger logger = LoggerFactory.getLogger(RestPublisher.class);

  /**
   * The method first converts observation to sendable Message and then publishes it to the message queue,
   * where it then is added to the message pool.
   *
   * @param observation
   */
  public static synchronized void publishObservation(Event observation) throws InterruptedException {
    try {
      RestTemplate restTemplate = new RestTemplate();
      Message observationMessage = createMessageFromObservation(observation);

      logger.info("TEST: Trying to publish " + observationMessage.getTopic() + " with id: " + observationMessage.getId() + " and value: " + observationMessage.getBody());

      restTemplate.postForLocation(Environment.MESSAGE_QUEUE_URL + "/api/v1/events", observationMessage);

      logger.info("Success");
    } catch (ResourceAccessException e) {
      logger.error("Unable to publish. Message Queue is not available or connection is refused!");
    }
  }

  private static Message createMessageFromObservation(Event event) {
    return new Message(event.getTopic(), Environment.SIMULATION_URL, EPriority.LOW, event.getBody());
  }
}

package dse_0207.machine_learning_microservice;

import dse_0207.shared_components.Environment;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.JSONWrapper;
import dse_0207.shared_components.Message.SubscriptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class MachineLearningMicroserviceApplication {
  private static final int MAX_RECONNECTION_ATTEMPTS = 20;
  private static final Logger logger =
      LoggerFactory.getLogger(MachineLearningMicroserviceApplication.class);

  public static void main(String[] args) throws Exception {
    int port;

    if (args.length == 0) {
      port = 3000;
      logger.info("No --server.port argument passed: using default port 8080");
    } else {
      port = Integer.parseInt(args[0].substring(14));
      logger.info("Using port " + port);
    }

    SpringApplication.run(MachineLearningMicroserviceApplication.class, args);

    RestTemplate restTemplate = new RestTemplate();

    List<ETopic> topics = new ArrayList<>();
    topics.add(ETopic.WEATHER_OBSERVATION);
    topics.add(ETopic.TEMPERATURE_OBSERVATION);

    SubscriptionMessage message =
        new SubscriptionMessage("subscribe", topics, Environment.MACHINE_LEARNING_URL, null);
    String URL = Environment.MESSAGE_QUEUE_URL + "/api/v1/subscriptions";

    int connectionAttempt = 1;
    while (true) {
      try {
        logger.info(
            "Attempting to subscribe on endpoint "
                + URL
                + " for topics: "
                + topics
                + "Attempt: "
                + connectionAttempt);
        JSONWrapper response = restTemplate.postForObject(URL, message, JSONWrapper.class);
        int subscriberId = Integer.parseInt(Objects.requireNonNull(response).values.get("id"));

        logger.info("Attempt to subscribe was successful. ID is " + subscriberId + ".");
        break;
      } catch (Exception e) {
        ++connectionAttempt;
        if (connectionAttempt > MAX_RECONNECTION_ATTEMPTS) {
          throw new Exception("Attempt to subscribe failed: " + e.getMessage());
        }

        logger.warn("Subscription failed. Attempting to reconnect in 10 seconds");
        e.printStackTrace();
        Thread.sleep(10000);
      }
    }
  }
}

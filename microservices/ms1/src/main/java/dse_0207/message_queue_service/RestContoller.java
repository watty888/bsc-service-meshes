package dse_0207.message_queue_service;

import dse_0207.shared_components.Message.DelayedReceivingMessage;
import dse_0207.shared_components.Message.JSONWrapper;
import dse_0207.shared_components.Message.Message;
import dse_0207.shared_components.Message.SubscriptionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
class Controller {
  MessageQueue mq = new MessageQueue();
  // https://logging.apache.org/log4j/2.x/manual/api.html
  private static final Logger logger = LogManager.getLogger("Controller");

  @PostMapping("/api/v1/events")
  public void events(@RequestBody Message message) {
    logger.info("Received message with Topic " + message.getTopic() + " and id " + message.getId());
    this.mq.addNewMessage(message);
    this.mq.publish();
  }

  @PostMapping("/api/v1/subscriptions")
  public ResponseEntity<JSONWrapper> subscribe(@Valid @RequestBody SubscriptionMessage message) {
    Map<String, String> response = new HashMap<>();
    if (message.action.equals("subscribe")) {
      try {
        Subscriber subscriber =
            new Subscriber(this.mq.getSubscribers().size() + 1, message.url, message.topics);
        this.mq.addSubscriber(subscriber);

        response.put("message", "Successfully registered for topics: " + message.topics);
        response.put("id", String.valueOf(subscriber.getId()));

        return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.OK);
      } catch (Exception e) {
        final String messageText =
            "Unable to subscribe service with id: " + this.mq.getSubscribers().size();
        response.put("message", messageText);
        return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.BAD_REQUEST);
      }

    } else if (message.action.equals("unsubscribe")) {
      if (message.id == null) {
        response.put("message", "You must provide an ID for unsubscribing");
        return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.BAD_REQUEST);
      }

      boolean isSuccessful = this.mq.removeSubscriberWithId(message.id);

      if (!isSuccessful) {
        response.put(
            "message", "Subscriber with id " + message.id + " was not found in the system.");
        return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.OK);
      } else {
        response.put("message", "Subscriber with id " + message.id + " successfully unsubscribed");
        return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.NOT_FOUND);
      }
    }

    response.put("message", "The action parameter must either be 'subscribe' or 'unsubscribe'");

    return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.BAD_REQUEST);
  }

  @PostMapping("/api/v1/delayed-receivers")
  public ResponseEntity<JSONWrapper> delayedReceivers(
      @RequestBody DelayedReceivingMessage message) {

    if (message.action.equals("add")) {
      return perfomAdditionToDelayedReceivers(message);
    } else if (message.action.equals("remove")) {
      return performRemovalFromDelayedReceivers(message);
    }

    Map<String, String> response = new HashMap<>();
    response.put("message", "The action parameter must either be 'add' or 'remove'");
    return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.NOT_ACCEPTABLE);
  }

  private ResponseEntity<JSONWrapper> perfomAdditionToDelayedReceivers(
      DelayedReceivingMessage message) {
    Map<String, String> response = new HashMap<>();
    if (this.mq.hasDelayedSubscriberWithId(message.id)) {
      response.put(
          "message", "Subscriber with id " + message.id + " is already marked for delayed receive");
    } else if (!this.mq.hasSubscriberWithId(message.id)) {
      response.put(
          "message",
          "No subscriber with id " + message.id + " found in the system. Please subscribe before.");
    } else {
      this.mq.markSubscriberForDelayedReceive(message.id);
      response.put(
          "message", "Subscriber " + message.id + " successfully marked for delayed receive");
    }

    return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.OK);
  }

  private ResponseEntity<JSONWrapper> performRemovalFromDelayedReceivers(
      DelayedReceivingMessage message) {
    Map<String, String> response = new HashMap<>();
    if (!this.mq.hasDelayedSubscriberWithId(message.id)) {
      response.put(
          "message",
          "Subscriber with id "
              + message.id
              + " is not marked for delayed receive. Nothing changed.");
    } else {
      this.mq.markSubscriberForImmediateReceive(message.id);
      response.put(
          "message", "Subscriber " + message.id + " successfully marked for immediate receive");
    }

    return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.OK);
  }

  /**
   * get cached messages for MS2
   *
   * @return ResponseEntity<Integer>
   */
  @GetMapping("/api/v1/cached")
  public ResponseEntity<Integer> cached() {
    return new ResponseEntity<>(mq.getMessagePool().size(), HttpStatus.OK);
  }

  /**
   * get exchanged messages for MS2
   *
   * @return ResponseEntity<Integer>
   */
  @GetMapping("/api/v1/exchanged")
  public ResponseEntity<Integer> exchanged() {
    return new ResponseEntity<>(mq.getExchangedMessagesTotal(), HttpStatus.OK);
  }

  /**
   * get subscribers messages for MS2
   *
   * @return Integer
   */
  @GetMapping("/api/v1/subscribers")
  public ResponseEntity<Integer> subscribersTotal() {
    return new ResponseEntity<>(mq.getSubscribersTotal(), HttpStatus.OK);
  }

  /**
   * get unsubscribed services for MS2
   *
   * @return Integer
   */
  @GetMapping("/api/v1/unsubscribed")
  public ResponseEntity<Integer> unsubscribedTotal() {
    return new ResponseEntity<>(mq.getUnsubscribedServices(), HttpStatus.OK);
  }

  /**
   * get observations for MS4
   *
   * @return ResponseEntity<List<Message>>
   */
  @GetMapping("api/v1/observations") // TODO: do we need to get a list of one type of observations?
  public ResponseEntity<List<Message>> observations() {
    return new ResponseEntity<>(mq.getObservations(), HttpStatus.OK);
  }

  /**
   * get anomalies for MS3
   *
   * @return ResponseEntity<List<Message>>
   */
  @GetMapping("api/v1/anomalies")
  public ResponseEntity<List<Message>> anomalies() {
    return new ResponseEntity<>(mq.getAnomalies(), HttpStatus.OK);
  }

  /**
   * get predictions for MS3
   *
   * @return ResponseEntity<List<Message>>
   */
  @GetMapping("api/v1/predictions")
  public ResponseEntity<List<Message>> predictions() {
    return new ResponseEntity<>(mq.getPredictions(), HttpStatus.OK);
  }

  /*
  Parts of this method was taken from https://www.baeldung.com/spring-boot-bean-validation
  Full credit to the original author.
  */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  // [...} MethodArgumentNotValidException exception as the exception to be handled
  // https://www.baeldung.com/spring-boot-bean-validation
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<JSONWrapper> handleValidationExceptions(
      MethodArgumentNotValidException exception) {
    Map<String, String> response = new HashMap<>();
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String errorMessage = error.getDefaultMessage();
              response.put("message", errorMessage);
            });

    return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.BAD_REQUEST);
  }
}

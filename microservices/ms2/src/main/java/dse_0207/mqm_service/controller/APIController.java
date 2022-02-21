package dse_0207.mqm_service.controller;

import dse_0207.mqm_service.RestPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    /**
     * shuts down the microservice
     * @return ResponseEntity<String>
     */
    @PostMapping("/api/v1/terminate")
    public ResponseEntity<String> terminate() {
        System.exit(0);
        return new ResponseEntity<>("Shutting down MS2...", HttpStatus.OK);
    }

    /**
     * sends RestTemplate.post to MS1 to retrieve all cached messages and return them to client
     * then returns the length of the list
     * @return Integer
     */
    @GetMapping("api/v1/cached")
    public Integer getCachedMessages() {
        return RestPublisher.getCached();
    }

    /**
     * sends RestTemplate.post to MS1 to retrieve all exchanged messages and return them to client
     * then returns the length of the list
     * @return Integer
     */
    @GetMapping("api/v1/exchanged")
    public Integer getExchangedMessages() {
        return RestPublisher.getExchanged();
    }

    /**
     * sends RestTemplate.post to MS1 to retrieve all subscribers and return them to client
     * it then returns the amount of subscribers
     * @return Integer
     */
    @GetMapping("api/v1/subscribers")
    public Integer getSubscribers() {
        return RestPublisher.getSubscribers();
    }


    /**
     * sends RestTemplate.post to MS1 to retrieve all subscribers and return them to client
     * it then returns the amount of subscribers
     * @return Integer
     */
    @GetMapping("api/v1/unsubscribed")
    public Integer getUnsubscribedServices() {
        return RestPublisher.getUnsubscribedServices();
    }
}

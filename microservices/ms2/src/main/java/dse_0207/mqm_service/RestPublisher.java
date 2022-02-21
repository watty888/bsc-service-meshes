package dse_0207.mqm_service;

import dse_0207.shared_components.Environment;
import org.springframework.web.client.RestTemplate;

public class RestPublisher {

    /**
     * returns the length of the list
     * @return Integer
     */
    public static Integer getCached() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Environment.MESSAGE_QUEUE_URL+"api/v1/cached",Integer.class);
    }

    /**
     * returns the length of the list
     * @return Integer
     */
    public static Integer getExchanged() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Environment.MESSAGE_QUEUE_URL+"api/v1/exchanged",Integer.class);
    }

    /**
     * returns the list of subscribers
     * @return Integer
     */
    public static Integer getSubscribers() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Environment.MESSAGE_QUEUE_URL+"api/v1/subscribers",Integer.class);
    }


    /**
     * returns the list of unsubscribed
     * @return Integer
     */
    public static Integer getUnsubscribedServices() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Environment.MESSAGE_QUEUE_URL+"api/v1/unsubscribed",Integer.class);
    }
}

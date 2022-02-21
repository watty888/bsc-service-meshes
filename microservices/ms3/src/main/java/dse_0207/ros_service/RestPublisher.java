package dse_0207.ros_service;

import dse_0207.shared_components.Environment;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

    public class RestPublisher {

        public RestPublisher(){

        }
        private RestTemplate restTemplate = new RestTemplate();
        private List<ETopic> topics = new ArrayList<>();


        private Message messageBuilder(List<?> data) {

            return null;
        }

        public void registerSelf() {
            topics.add(ETopic.TEMPERATURE_ANOMALY);
            topics.add(ETopic.TEMPERATURE_PREDICTION);
            topics.add(ETopic.WEATHER_ANOMALY);
            topics.add(ETopic.WEATHER_PREDICTION);


            Subscriber selfInfo = new Subscriber(1, "http://localhost:8080", topics, "subscribe");

            restTemplate.postForObject(Environment.MESSAGE_QUEUE_URL + "api/v1/subscriptions", selfInfo, String.class);
        }




    }



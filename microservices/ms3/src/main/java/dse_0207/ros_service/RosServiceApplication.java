package dse_0207.ros_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RosServiceApplication.class, args);
        new RestPublisher().registerSelf();

    }

}

package dse_0207.machine_learning_microservice.controller;

import dse_0207.machine_learning_microservice.Publisher;
import dse_0207.machine_learning_microservice.machine_learning_anomaly.Anomaly;
import dse_0207.machine_learning_microservice.machine_learning_anomaly.AnomalyJob;
import dse_0207.machine_learning_microservice.machine_learning_anomaly.AnomalyService;
import dse_0207.machine_learning_microservice.machine_learning_prediction.Prediction;
import dse_0207.machine_learning_microservice.machine_learning_prediction.PredictionJob;
import dse_0207.machine_learning_microservice.machine_learning_prediction.PredictionService;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.JSONWrapper;
import dse_0207.shared_components.Message.Message;
import java.util.Arrays;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private static Logger logger = LoggerFactory.getLogger(Controller.class);
    private PredictionService predictionService = new PredictionService();
    private AnomalyService anomalyService = new AnomalyService();
    private List<Message> messages = new ArrayList<>();
    private Publisher publisher = new Publisher();

    @PostMapping("/api/v1/terminate")
    public void terminate() {
        logger.info("MS5 instance will shut down.");
        System.exit(0);
    }

    @PostMapping(value = "/api/v1/events")
    public ResponseEntity<JSONWrapper> events(@RequestBody(required = false) Message message) {
        this.messages.add(message);

        Thread predictionJob = new Thread(new PredictionJob(this.predictionService, this.messages));
        predictionJob.start();

        Thread anomalyJob = new Thread(new AnomalyJob(this.anomalyService, this.messages));
        anomalyJob.start();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Event successfully processed.");

        logger.info("Event successfully processed");
        return new ResponseEntity<>(new JSONWrapper(response), HttpStatus.OK);
    }

    @GetMapping("/api/v1/predictions")
    public List<Prediction> predictions(@RequestParam(required = false) Integer limit, @RequestParam(required = false) List<ETopic> topics, @RequestParam(required = false) String order) {
        if (topics == null) {
            // https://stackoverflow.com/a/27645804
            topics = Arrays.asList(ETopic.class.getEnumConstants());
        }

        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }

        if (order == null) {
            order = "ASC";
        }

        return this.predictionService.getPredictions(topics, limit, order);
    }

    @GetMapping("/api/v1/anomalies")
    public List<Anomaly> anomalies(@RequestParam(required = false) Integer limit, @RequestParam(required = false) List<ETopic> topics, @RequestParam(required = false) String order) {
        if (topics == null) {
            // https://stackoverflow.com/a/27645804
            topics = Arrays.asList(ETopic.class.getEnumConstants());
        }

        if (limit == null) {
            limit = Integer.MAX_VALUE;
        }

        if (order == null) {
            order = "ASC";
        }

        return this.anomalyService.getAnomalies(topics, limit, order);
    }
}

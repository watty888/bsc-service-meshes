package dse_0207.machine_learning_microservice.machine_learning_prediction;

import dse_0207.shared_components.Message.ETopic;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PredictionRepositoryTest {
    private PredictionRepository predictionRepository;

    @BeforeEach
    void setUp() {
        this.predictionRepository = new PredictionRepository();

        predictionRepository.savePrediction(new Prediction(ETopic.WEATHER_OBSERVATION, "Door close"));
        predictionRepository.savePrediction(new Prediction(ETopic.WEATHER_OBSERVATION, "Door close"));
        predictionRepository.savePrediction(new Prediction(ETopic.WEATHER_OBSERVATION, "Door open"));
        predictionRepository.savePrediction(new Prediction(ETopic.WEATHER_OBSERVATION, "Door open"));
        predictionRepository.savePrediction(new Prediction(ETopic.WEATHER_OBSERVATION, "Door close"));
    }

    @Test
    public void searchingByTopicShouldReturnListOnlyContainingTheTopic() {
        List<ETopic> topics = new ArrayList<>();
        topics.add(ETopic.WEATHER_OBSERVATION);

        Map<ETopic, List<Prediction>> predictions = predictionRepository.getPredictionsForTopics(topics);

        for(Map.Entry<ETopic, List<Prediction>> entry : predictions.entrySet()) {
            List<Prediction> currentPredictions = entry.getValue();
            for(Prediction p: currentPredictions) {
                assertEquals(p.getTopic(), ETopic.WEATHER_OBSERVATION);
            }
        }
    }

    @Test
    void PredictionsAreTreatedNotSameIfOnlyOnePredictionWasMade() {
        List<Prediction> samplePredictions = new ArrayList<>();
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));

        assertEquals(this.predictionRepository.lastPredictionDiffersFromPrevious(samplePredictions, ETopic.WEATHER_OBSERVATION), true);
    }

    @Test
    void PredictionsShouldCorrectlyBeTreatedAsDifferent() {
        List<Prediction> samplePredictions = new ArrayList<>();
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.TEMPERATURE_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "36.3"));

        assertEquals(this.predictionRepository.lastPredictionDiffersFromPrevious(samplePredictions, ETopic.WEATHER_OBSERVATION), true);
    }

    @Test
    void PredictionsShouldCorrectlyBeTreatedAsTheSame() {
        List<Prediction> samplePredictions = new ArrayList<>();
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.TEMPERATURE_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));

        assertEquals(this.predictionRepository.lastPredictionDiffersFromPrevious(samplePredictions, ETopic.WEATHER_OBSERVATION), false);
    }

    @Test
    void FilteringPredictionsShouldReturnCorrectValue() {
        List<Prediction> samplePredictions = new ArrayList<>();
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.TEMPERATURE_OBSERVATION, "33.3"));
        samplePredictions.add(new Prediction(ETopic.WEATHER_OBSERVATION, "33.3"));

        List<Prediction> filteredPredictions = this.predictionRepository.getFilteredPredictions(samplePredictions, ETopic.WEATHER_OBSERVATION);
        for (Prediction p: filteredPredictions) {
            assertEquals(p.getTopic(), ETopic.WEATHER_OBSERVATION);
        }

        assertEquals(filteredPredictions.size(), 3);
    }
}
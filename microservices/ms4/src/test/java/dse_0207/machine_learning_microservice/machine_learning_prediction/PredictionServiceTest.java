package dse_0207.machine_learning_microservice.machine_learning_prediction;

import org.junit.jupiter.api.BeforeEach;

class PredictionServiceTest {
    private PredictionService predictionService;

    @BeforeEach
    void setUp() {
        this.predictionService = new PredictionService();
    }
}
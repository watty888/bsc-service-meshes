package dse_0207.machine_learning_microservice.machine_learning_prediction;

import dse_0207.shared_components.Message.ETopic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PredictionRepository {
    private Map<ETopic, List<Prediction>> predictions;
    private List<Prediction> listPredictions = new ArrayList<>();

    public PredictionRepository() {
        this.predictions = new HashMap<>();
    }

    public synchronized Map<ETopic, List<Prediction>> getPredictionsForTopics(List<ETopic> topics) {
        Map<ETopic, List<Prediction>> result = new HashMap<>();

        for (ETopic topic: topics) {
            if (this.predictions.get(topic) != null) {
                result.put(topic, this.predictions.get(topic));
            }
        }

        return result;
    }

    public synchronized List<Prediction> getPredictions(List<ETopic> topics, int limit, String order) {
        List<Prediction> filteredPredictions = this.listPredictions.stream()
            .filter(prediction -> topics.contains(prediction.getTopic()))
            .collect(Collectors.toList());

        if (order.equals("DESC")) {
            Collections.reverse(filteredPredictions);
        }

        limit = filteredPredictions.size() < limit ? filteredPredictions.size() : limit;
        return filteredPredictions.subList(0, limit);
    }

    public synchronized void savePrediction(Prediction prediction) {
        List<Prediction> predictions = this.predictions.get(prediction.getTopic());

        if (predictions == null) {
            List<Prediction> newList = new ArrayList<>();
            newList.add(prediction);

            this.predictions.put(prediction.getTopic(), newList);
        } else {
            predictions.add(prediction);
            this.predictions.put(prediction.getTopic(), predictions);
        }

        this.listPredictions.add(prediction);
    }

    public synchronized List<Prediction> getPredictionsForTopic(ETopic topic) {
        return this.listPredictions.stream()
            .filter(prediction -> prediction.getTopic().equals(topic))
            .collect(Collectors.toList());
    }

    public synchronized boolean lastPredictionDiffersFromPrevious(List<Prediction> allPredictions, ETopic topic) {
        List<Prediction> predictions = this.getFilteredPredictions(allPredictions, topic);
        if (predictions == null) {
            return false;
        }

        int predictionsSize = predictions.size();

        if (predictionsSize == 1) {
            return true;
        }

        int lastIndex = predictionsSize - 1;
        int secondLastIndex = predictionsSize - 2;

        Prediction lastPrediction = predictions.get(lastIndex);
        Prediction secondLastPrediction = predictions.get(secondLastIndex);

        return !lastPrediction.getBody().equals(secondLastPrediction.getBody());
    }

    public synchronized List<Prediction> getFilteredPredictions(List<Prediction> predictions, ETopic topic) {
        List<Prediction> filteredPredictions = new ArrayList<>();

        for (Prediction p: predictions) {
            if (p.getTopic().equals(topic)) {
                filteredPredictions.add(p);
            }
        }

        return filteredPredictions;
    }
}

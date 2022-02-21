package dse_0207.machine_learning_microservice.machine_learning_prediction.prediction_model;

import dse_0207.machine_learning_microservice.machine_learning_prediction.Prediction;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProbabilityPredictionModel extends PredictionModel {
    /**
     * Calculates predictions based on a probability model
     * @param messages A list of messages
     * @return The calculated prediction
     */
    public Prediction getPrediction(List<Message> messages, ETopic topic) {
        List<Message> filteredMessages = super.getMessagesWithTopic(messages, topic);

        Set<String> uniqueMessageBodyValues = getUniqueBodyValues(filteredMessages);

        Map<String, List<Integer>> messageBodyWithOccurrence = new HashMap<>();

        for (String bodyValue: uniqueMessageBodyValues) {
            List<Integer> indexOfMatchingMessages = new ArrayList<>();

            for (int i = 0; i < filteredMessages.size(); i += 1) {
                Message currentMessage = filteredMessages.get(i);
                if (currentMessage.getBody().equals(bodyValue)) {
                    indexOfMatchingMessages.add(i);
                }
            }

            messageBodyWithOccurrence.put(bodyValue, indexOfMatchingMessages);
        }

        int highestOccurrence = -1;
        String highestBodyValueOccurrence = "";
        int lastMessageIndexWithBodyValue = -1;

        for (Map.Entry<String, List<Integer>> matchingIndexes: messageBodyWithOccurrence.entrySet()) {
            if (matchingIndexes.getValue().size() > highestOccurrence) {
                highestOccurrence = matchingIndexes.getValue().size();
                highestBodyValueOccurrence = matchingIndexes.getKey();

                int matchingMessagesSize = matchingIndexes.getValue().size();
                lastMessageIndexWithBodyValue = matchingIndexes.getValue().get(matchingMessagesSize - 1);

            } else if (matchingIndexes.getValue().size() == highestOccurrence) {
                int matchingMessagesSize = matchingIndexes.getValue().size();

                if (lastMessageIndexWithBodyValue < matchingIndexes.getValue().get(matchingMessagesSize - 1)) {
                    highestBodyValueOccurrence = matchingIndexes.getKey();
                    lastMessageIndexWithBodyValue = matchingIndexes.getValue().get(matchingMessagesSize - 1);
                }
            }
        }

        return new Prediction(super.getPredictionTopicLabelFromObservationTopic(topic), highestBodyValueOccurrence);
    }

    private static Set<String> getUniqueBodyValues(List<Message> messages) {
        Set<String> uniqueMessageBodyValues = new HashSet<>();

        for (Message message: messages) {
            uniqueMessageBodyValues.add(message.getBody());
        }

        return uniqueMessageBodyValues;
    }
}

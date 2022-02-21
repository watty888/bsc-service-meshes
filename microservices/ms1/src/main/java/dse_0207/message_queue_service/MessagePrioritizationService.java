package dse_0207.message_queue_service;

import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;

public class MessagePrioritizationService {
    public List<Message> sortByPriority(List<Message> messages) {
        List<Message> prioritizedMessages = new ArrayList<>();

        // Very rudimentary
        for (Message message: messages) {
            if (message.getPriority().equals(EPriority.HIGH)) {
                prioritizedMessages.add(message);
            }
        }

        for (Message message: messages) {
            if (message.getPriority().equals(EPriority.LOW)) {
                prioritizedMessages.add(message);
            }
        }

        return prioritizedMessages;
    }
}

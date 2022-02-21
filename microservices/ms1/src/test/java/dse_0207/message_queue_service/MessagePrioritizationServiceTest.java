package dse_0207.message_queue_service;

import static org.junit.jupiter.api.Assertions.*;

import dse_0207.shared_components.Message.EPriority;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MessagePrioritizationServiceTest {

  @Test
  void SortingByPriorityShouldReturnCorrectMessages() {
    MessagePrioritizationService service = new MessagePrioritizationService();

    List<Message> testMessages = new ArrayList<>();
    testMessages.add(new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.HIGH, "test"));
    testMessages.add(new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.LOW, "test"));
    testMessages.add(new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.LOW, "test"));
    testMessages.add(new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.HIGH, "test"));
    testMessages.add(new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.HIGH, "test"));
    testMessages.add(new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.LOW, "test"));
    testMessages.add(new Message("test-id", ETopic.TEMPERATURE_OBSERVATION, "test-url", EPriority.HIGH, "test"));

    List<Message> prioritizedMessages = service.sortByPriority(testMessages);

    List<Message> prioritizedMessagesWithHighPriority = prioritizedMessages.subList(0, 4);
    List<Message> prioritizedMessagesWithLowPriority = prioritizedMessages.subList(4, 7);

    for (Message message: prioritizedMessagesWithHighPriority) {
      assertEquals(message.getPriority(), EPriority.HIGH);
    }

    for (Message message: prioritizedMessagesWithLowPriority) {
      assertEquals(message.getPriority(), EPriority.LOW);
    }
  }
}
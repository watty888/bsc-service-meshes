package dse_0207.message_queue_service;

import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageDelayService {
    // The MQ will publish 20 times before the
    // delay service will also publish it's messages
    private static int DELAY_THRESHOLD = 2;
    private List<Subscriber> delayedSubscribers;
    private List<Message> delayedMessages;
    private List<Message> deliveredDelayedMessages;
    private int delayCounter;

    public MessageDelayService() {
        this.delayedMessages = new ArrayList<>();
        this.delayedSubscribers = new ArrayList<>();
        this.deliveredDelayedMessages = new ArrayList<>();
        this.delayCounter = 0;
    }

    public void copyIfInterested(Message m) {
        for(Subscriber subscriber: delayedSubscribers) {
                if (subscriber.isInterested(m.getTopic())) {
                delayedMessages.add(m);
                break;
            }
        }
    }

    public void addSubscriber(Subscriber s) {
        delayedSubscribers.add(s);
    }

    public void publishIfNeeded() {
        if (delayCounter == DELAY_THRESHOLD) {
            delayCounter = 0;
            publishMessages();
        } else {
            delayCounter += 1;
        }
    }

    private void publishMessages() {
        try {
            List<Message> prioritizedMessages = new MessagePrioritizationService().sortByPriority(this.delayedMessages);
            boolean isSuccessful = RestPublisher.publish(prioritizedMessages, this.delayedSubscribers);
            if (isSuccessful) {
                this.deliveredDelayedMessages = this.delayedMessages;
                this.delayedMessages = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Subscriber> getDelayedSubscribers() {
        return delayedSubscribers;
    }

    public List<Message> getDelayedMessages() {
        return delayedMessages;
    }

    public void setDelayedSubscribers(List<Subscriber> delayedSubscribers) {
        this.delayedSubscribers = delayedSubscribers;
    }

    public void setDelayedMessages(List<Message> delayedMessages) {
        this.delayedMessages = delayedMessages;
    }

    public boolean removeSubscriberWithId(Integer id) {
        int subscriberIndex = getSubscriberIndex(id);

        if (subscriberIndex == -1) {
            return false;
        }

        this.delayedSubscribers.remove(subscriberIndex);
        return true;
    }

    private int getSubscriberIndex(Integer id) {
        int subscriberIndex = -1;
        for (int i = 0; i < this.delayedSubscribers.size(); i += 1) {
            if (this.delayedSubscribers.get(i).getId().equals(id)) {
                subscriberIndex = i;
                break;
            }
        }
        return subscriberIndex;
    }

    public boolean hasSubscriberWithId(Integer id) {
        for (Subscriber subscriber: this.delayedSubscribers) {
            if (subscriber.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    public Subscriber getSubscriberWithId(Integer id) {
        return this.delayedSubscribers.get(getSubscriberIndex(id));
    }
}

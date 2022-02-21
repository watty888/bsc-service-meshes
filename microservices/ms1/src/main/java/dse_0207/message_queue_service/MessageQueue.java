package dse_0207.message_queue_service;

import dse_0207.shared_components.Message.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageQueue {
    private final List<Subscriber> subscriberList = new ArrayList<>();
    private List<Message> messagePool = new ArrayList<>();
    private final MessagePrioritizationService messagePrioritizationService = new MessagePrioritizationService();
    private final MessageDelayService messageDelayService = new MessageDelayService();
    private Integer exchangedMessagesTotal = 0;
    private Integer unsubscribedServices = 0;

    public void addNewMessage(Message m) {
        messagePool.add(m);
        exchangedMessagesTotal++;
        this.messageDelayService.copyIfInterested(m);
    }

    public Integer getExchangedMessagesTotal() { return exchangedMessagesTotal; }

    public List<Message> getMessagePool() { return messagePool; }

    public List<Message> getObservations() {
        // TODO: go through messagePool, filter all observations and return them
        return null;
    }

    public List<Message> getAnomalies() {
        // TODO: go through messagePool, filter all anomalies and return them
        return null;
    }

    public List<Subscriber> getSubscribers() {
        return subscriberList;
    }

    public void addSubscriber(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }

    public List<Message> getPredictions() {
        // TODO: go through messagePool, filter all predictions and return them
        return null;
    }

    public void publish() {
        List<Message> prioritizedMessages = this.messagePrioritizationService.sortByPriority(this.messagePool);
        boolean isSuccessful = RestPublisher.publish(prioritizedMessages, this.subscriberList);
        if (isSuccessful) {
            List<Message> deliveredMessages = this.messagePool;
            this.messagePool = new ArrayList<>();
        }

        this.messageDelayService.publishIfNeeded();
    }

    public boolean removeSubscriberWithId(Integer id) {
        int subscriberIndex = getSubscriberIndex(id);

        // Could not find the subscriber in the message queue
        // maybe he's in the delayed receivers list.
        if (subscriberIndex == -1) {
            return this.messageDelayService.removeSubscriberWithId(id);
        }

        this.subscriberList.remove(subscriberIndex);
        unsubscribedServices++;
        return true;
    }

    private int getSubscriberIndex(Integer id) {
        int subscriberIndex = -1;
        for (int i = 0; i < this.subscriberList.size(); i += 1) {
            if (this.subscriberList.get(i).getId().equals(id)) {
                subscriberIndex = i;
                break;
            }
        }
        return subscriberIndex;
    }

    public boolean hasDelayedSubscriberWithId(Integer id) {
        return this.messageDelayService.hasSubscriberWithId(id);
  }

    public void markSubscriberForDelayedReceive(Integer id) {
        int subscriberIndex = getSubscriberIndex(id);

        Subscriber subscriber = this.subscriberList.get(subscriberIndex);
        this.subscriberList.remove(subscriberIndex);

        this.messageDelayService.addSubscriber(subscriber);
    }

    public boolean hasSubscriberWithId(Integer id) {
        return getSubscriberIndex(id) != -1;
    }

    public void markSubscriberForImmediateReceive(Integer id) {
        Subscriber subscriber =  this.messageDelayService.getSubscriberWithId(id);
        this.messageDelayService.removeSubscriberWithId(id);

        this.subscriberList.add(subscriber);
    }

    public Integer getSubscribersTotal() {
        return subscriberList.size();
    }

    public Integer getUnsubscribedServices() {
        return unsubscribedServices;
    }
}

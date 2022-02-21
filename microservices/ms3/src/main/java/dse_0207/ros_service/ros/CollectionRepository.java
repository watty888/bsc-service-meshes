package dse_0207.ros_service.ros;

import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;


import java.util.*;

public class CollectionRepository {

    private Map<ETopic, List<Message>> collection = new HashMap<>();
    private List<Message> messages = new ArrayList<>();

    public CollectionRepository() {
    }

    public boolean addMessage(Message message) {
        if (collection.get(message.getTopic()) == null) {
            List<Message> newList = new ArrayList<>();
            newList.add(message);

            this.collection.put(message.getTopic(), newList);
        } else {
            List<Message> tmp = collection.get(message.getTopic());
            tmp.add(message);
            this.collection.put(message.getTopic(), tmp);
        }

        this.messages.add(message);

        return true;
    }


    public List<Message> getMessages(ETopic topic) {
        return collection.get(topic);
    }


    public List<Message> getMessages(List<ETopic> topics) {
        List<Message> tmp = new ArrayList<>();

        if(topics != null) {
            for (ETopic t : topics) {
             if (collection.get(t)!= null){
                for (Message m : collection.get(t)) {
                    tmp.add(m);
                }
             }
            }
            return tmp;
        }
        else return null;
    }
}

package dse_0207.ros_service.ros;

import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;

import java.util.List;

public class ResultOutputService{
    private CollectionRepository collections = new CollectionRepository();

    public ResultOutputService(){}


    public List<Message> getMessages (List<ETopic> topics){
        return collections.getMessages(topics);
    }

    public boolean addMessage(Message message){
        collections.addMessage(message);
        return true;
    }
}

package dse_0207.ros_service.controller;

import dse_0207.ros_service.RestPublisher;
import dse_0207.ros_service.ros.ResultOutputService;
import dse_0207.shared_components.Message.ETopic;
import dse_0207.shared_components.Message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@org.springframework.web.bind.annotation.RestController
public class RestController {
    RestTemplate rt = new RestTemplate();
    ResultOutputService resultOutputService = new ResultOutputService();
    RestPublisher publisher ;

    List<Message> messages = new ArrayList<>();




    @PostMapping("/api/v1/terminate")
    public String terminate(){
        return "Result Output instance will be shut down";
    }

    @PostMapping("/api/v1/events")
    public void events(@RequestBody(required = false) Message message){
      resultOutputService.addMessage(message);
    }


    @GetMapping(value = "/api/v1/webservice")
    public ResponseEntity<List<Message>> addToCollection(@RequestParam(required = false) List<ETopic> topics){
        if (topics == null) {
           // https://stackoverflow.com/a/27645804
            topics = Arrays.asList(ETopic.class.getEnumConstants());
        }
        List<Message> mes = resultOutputService.getMessages(topics);

        return new ResponseEntity<List<Message>>(mes, HttpStatus.OK);
    }



}


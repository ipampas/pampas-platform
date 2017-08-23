package com.ipampas.ui.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RestOperations restOperations;
    @Value("${messages.url:http://localhost:7777}/api")
    private String messagesUrl;
    @Autowired
    private MessageClient messageClient;

    @RequestMapping("/")
    public String home(Model model) {
        //List<Message> messages = Arrays.asList(restOperations.getForObject(messagesUrl + "/messages", Message[].class));
        List<Message> messages = messageClient.getMessages();
        model.addAttribute("messages", messages);
        return "index";
    }

    @RequestMapping(path = "messages", method = RequestMethod.POST)
    public String postMessages(@RequestParam String text) {
        Message message = new Message();
        message.text = text;
        /*restOperations.exchange(RequestEntity
                .post(UriComponentsBuilder.fromHttpUrl(messagesUrl).pathSegment("messages").build().toUri())
                .body(message), Message.class);*/
        messageClient.createMessage(message);
        return "redirect:/";
    }

    public static class Message {
        public String text;
        public String username;
        public LocalDateTime createdAt;
    }

    @FeignClient("pampas-resource-server")
    public interface MessageClient {
        @RequestMapping(method = RequestMethod.GET, value = "/api/messages")
        List<Message> getMessages();

        @RequestMapping(method = RequestMethod.POST, value = "/api/messages", consumes = "application/json")
        void createMessage(Message message);
    }

}

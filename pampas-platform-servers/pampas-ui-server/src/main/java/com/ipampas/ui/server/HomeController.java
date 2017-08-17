package com.ipampas.ui.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RestOperations restOperations;
    @Value("${messages.url:http://localhost:7777}/api")
    String messagesUrl;

    @RequestMapping("/")
    public String home(Model model) {
        List<Message> messages = Arrays.asList(restOperations.getForObject(messagesUrl + "/messages", Message[].class));
        model.addAttribute("messages", messages);
        return "index";
    }

    @RequestMapping(path = "messages", method = RequestMethod.POST)
    public String postMessages(@RequestParam String text) {
        Message message = new Message();
        message.text = text;
        restOperations.exchange(RequestEntity
                .post(UriComponentsBuilder.fromHttpUrl(messagesUrl).pathSegment("messages").build().toUri())
                .body(message), Message.class);
        return "redirect:/";
    }

    public static class Message {
        public String text;
        public String username;
        public LocalDateTime createdAt;
    }

}

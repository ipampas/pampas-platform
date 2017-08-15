package com.ipampas.resource.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MessageResource {

    private List<Message> messages = Collections.synchronizedList(new LinkedList<>());

    @GetMapping(path = "api/messages")
    public List<Message> getMessages(Principal principal) {
        return messages;
    }

    @PostMapping(path = "api/messages")
    public Message postMessage(Principal principal, @RequestBody Message message) {
        message.username = principal.getName();
        message.createdAt = LocalDateTime.now();
        messages.add(0, message);
        return message;
    }

    public static class Message {
        public String text;
        public String username;
        public LocalDateTime createdAt;
    }

}

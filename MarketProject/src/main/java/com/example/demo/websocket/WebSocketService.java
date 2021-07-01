package com.example.demo.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessageSendingOperations template;

    public void sendMessage(String string){
        template.convertAndSend("/topic/messages",
                new MessageHolder("The following item(s) are out of stock: " + string).getMessage()
        );
    }
}

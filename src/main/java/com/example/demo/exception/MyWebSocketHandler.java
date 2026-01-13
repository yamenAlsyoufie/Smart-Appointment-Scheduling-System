package com.example.demo.exception;


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String clientMessage = message.getPayload();
        System.out.println("Received: " + clientMessage);

        // Send response back to client
        String response = "Server received: " + clientMessage;
        session.sendMessage(new TextMessage(response));
    }
}

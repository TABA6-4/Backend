package com.example.focus;

import org.junit.jupiter.api.Test;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

public class PhotoWebSocketTest {

    @Test
    public void testSendPhoto() throws Exception {
        String wsUrl = "ws://localhost:8080/photo";

        StandardWebSocketClient client = new StandardWebSocketClient();
        try {
            WebSocketSession session = client.doHandshake(new AbstractWebSocketHandler() {
                @Override
                public void handleTextMessage(WebSocketSession session, TextMessage message) {
                    System.out.println("서버 응답: " + message.getPayload());
                }
            }, wsUrl).get();

            File photoFile = new File("src/test/resources/sample.jpg");
            try (FileInputStream fis = new FileInputStream(photoFile)) {
                byte[] photoBytes = fis.readAllBytes();
                session.sendMessage(new org.springframework.web.socket.BinaryMessage(ByteBuffer.wrap(photoBytes)));
            }

            Thread.sleep(2000);
            session.close();
        } catch (Exception e) {
            System.err.println("WebSocket 연결 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

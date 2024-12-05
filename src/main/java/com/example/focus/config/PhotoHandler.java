package com.example.focus.config;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.ByteBuffer;

public class PhotoHandler extends BinaryWebSocketHandler {
    private static final String FLASK_SERVER_URL = "http://localhost:5000/process-image";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket 연결 성공");
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        ByteBuffer payload = message.getPayload();
        byte[] photoBytes = new byte[payload.remaining()];
        payload.get(photoBytes);

        sendToFlaskServer(photoBytes);
        session.sendMessage(new TextMessage("사진이 성공적으로 처리되었습니다."));
    }

    private void sendToFlaskServer(byte[] photoBytes) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(photoBytes, headers);
        String response = restTemplate.postForObject(FLASK_SERVER_URL, requestEntity, String.class);

        System.out.println("Flask 서버 응답: " + response);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 연결 종료");
    }
}

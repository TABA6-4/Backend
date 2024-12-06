package com.example.focus.config;

import com.example.focus.dto.videoSession.VideoSessionImageDTO;
import com.example.focus.entity.VideoSession;
import com.example.focus.service.FlaskService;
import com.example.focus.service.VideoSessionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@CrossOrigin
@Component
public class ImageWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private VideoSessionService videoSessionService;

    @Autowired
    private FlaskService flaskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신된 메시지를 파싱
        String payload = message.getPayload();
        VideoSessionImageDTO imageData = objectMapper.readValue(payload, VideoSessionImageDTO.class);

        // DB에 user_id와 이미지 제목 저장
        VideoSession videoSession = videoSessionService.createSession(imageData.getUser_id(), imageData.getTitle());

        // AI 서버로 데이터 전송
        flaskService.sendToAIServer(imageData.getImage(), videoSession.getSession_id());

        // WebSocket 응답
        session.sendMessage(new TextMessage("Data processed successfully"));
    }
}

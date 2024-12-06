package com.example.focus.config;

import com.example.focus.dto.videoSession.VideoSessionImageDTO;
import com.example.focus.entity.VideoSession;
import com.example.focus.service.FlaskService;
import com.example.focus.service.VideoSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImageWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private VideoSessionService videoSessionService;

    @Autowired
    private FlaskService flaskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 1. 메시지 수신
        String payload = message.getPayload();

        // 2. JSON -> DTO 변환
        VideoSessionImageDTO imageData = objectMapper.readValue(payload, VideoSessionImageDTO.class);

        // 3. DB 저장: user_id와 이미지 제목을 기반으로 VideoSession 생성
        VideoSession videoSession = videoSessionService.createSession(imageData.getUser_id(), imageData.getTitle());

        // 4. AI 서버로 데이터 전송
        flaskService.sendToAIServer(imageData.getImage(), videoSession.getSession_id());

        // 5. WebSocket 응답 전송
        session.sendMessage(new TextMessage("Data processed successfully"));
        log.info("Image processed successfully");
    }
}

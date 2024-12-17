package com.example.focus.config;

import com.example.focus.entity.VideoSession;
import com.example.focus.service.FlaskService;
import com.example.focus.service.VideoSessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class ImageWebSocketHandler extends AbstractWebSocketHandler {
    @Autowired
    private VideoSessionService videoSessionService;

    @Autowired
    private FlaskService flaskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 연결된 클라이언트를 관리하는 세션 리스트
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.add(session);
//        log.info("New session connected: {}", session.getId());
//    }
////
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session);
//        log.info("Session disconnected: {}", session.getId());
//    }


    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        // 바이너리 메시지 읽기
        byte[] payload = message.getPayload().array();

        // 메타데이터와 바이너리 데이터 분리
        int separatorIndex = findSeparatorIndex(payload);
        if (separatorIndex == -1) {
            throw new IOException("Invalid message format: No separator found");
        }

        // JSON 메타데이터 추출
        String jsonMetadata = new String(payload, 0, separatorIndex, StandardCharsets.UTF_8);
        Map<String, Object> metadata = objectMapper.readValue(jsonMetadata, Map.class);

        Long userId = ((Number) metadata.get("user_id")).longValue();
        String title = (String) metadata.get("title");

        // 바이너리 이미지 데이터 추출
        byte[] imageBytes = Arrays.copyOfRange(payload, separatorIndex + 1, payload.length);

        System.out.println("Metadata received: user_id=" + userId + ", title=" + title);
        System.out.println("Image size: " + imageBytes.length + " bytes");

        // 3. DB 저장: user_id와 이미지 제목을 기반으로 VideoSession 생성
        VideoSession videoSession = videoSessionService.findSession(userId, title);
        if (videoSession == null) {
            // 세션이 없을 경우 처리
            log.error("No video session found for user_id={} and title={}", userId, title);
            session.sendMessage(new TextMessage("Error: No video session found for the given user_id and title."));
            return;
        }
        // 4. AI 서버로 데이터 전송
        String result = flaskService.sendToAIServer(imageBytes, videoSession.getSession_id());

        // 클라이언트로 응답 전송
        session.sendMessage(new BinaryMessage("Image and metadata processed successfully".getBytes()));
        session.sendMessage(new TextMessage(result));
        log.info("Image processed successfully");
    }

//    /**
//     * 서버에서 임의로 알림을 보내는 메서드
//     *
//     * @param message 클라이언트에 보낼 메시지
//     */
//    public void sendServerNotification(String message) {
//        synchronized (sessions) {
//            for (WebSocketSession session : sessions) {
//                if (session.isOpen()) {
//                    try {
//                        session.sendMessage(new TextMessage(message));
//                        log.info("Notification sent to session: {}", session.getId());
//                    } catch (IOException e) {
//                        log.error("Error sending notification to session: {}", session.getId(), e);
//                    }
//                }
//            }
//        }
//    }

    /**
     * 바이너리 메시지에서 JSON 메타데이터와 이미지 데이터를 구분하는 구분자(\n)의 인덱스 찾기
     */
    private int findSeparatorIndex(byte[] payload) {
        for (int i = 0; i < payload.length; i++) {
            if (payload[i] == '\n') { // 구분자로 '\n' 사용
                return i;
            }
        }
        return -1;
    }
}

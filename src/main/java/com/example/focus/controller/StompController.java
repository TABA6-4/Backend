package com.example.focus.controller;

import com.example.focus.entity.MessageData;
import com.example.focus.entity.VideoSession;
import com.example.focus.repository.VideoSessionRepository;
import com.example.focus.service.VideoSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.UUID;

@Controller
public class StompController {

    @Autowired
    private VideoSessionService videoSessionService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final String FLASK_SERVER_URL = "http://localhost:5000/process-image";

    @MessageMapping("/upload")
    public void handleImageUpload(MessageData messageData) throws Exception {
        //VideoSession 조회
        VideoSession videoSession = videoSessionService.createSession(messageData);

        // Flask로 데이터 전송
        sendToFlask(messageData.getImageBytes(), videoSession.getSession_id());

        // 클라이언트에 결과 전송
        messagingTemplate.convertAndSend("/topic/updates", "Image processing started for session: " + videoSession.getSession_id());
    }
    //flask로 전송
    private void sendToFlask(byte[] imageBytes, Long sessionId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(imageBytes, headers);
        restTemplate.postForObject(FLASK_SERVER_URL + "?session_id=" + sessionId, requestEntity, String.class);
    }
}

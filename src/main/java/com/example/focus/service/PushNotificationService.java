package com.example.focus.service;

import com.example.focus.config.ImageWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    @Autowired
    private ImageWebSocketHandler imageWebSocketHandler;

    /**
     * 푸시 알림을 전송하는 메서드
     *
     * @param message 전송할 메시지
     */
    public void sendNotification(String message) {
        imageWebSocketHandler.sendServerNotification(message);
    }
}

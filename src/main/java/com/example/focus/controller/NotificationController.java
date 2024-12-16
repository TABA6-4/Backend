package com.example.focus.controller;

import com.example.focus.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

//    @Autowired
//    private PushNotificationService pushNotificationService;
//
//    /**
//     * 푸시 알림 테스트용 엔드포인트
//     */
//    @PostMapping("/send-notification")
//    public String sendNotification(@RequestParam String message) {
//        pushNotificationService.sendNotification(message);
//        return "Notification sent: " + message;
//    }
}

package com.example.focus.service;


import com.example.focus.entity.User;
import com.example.focus.entity.VideoSession;
import com.example.focus.repository.UserRepository;
import com.example.focus.repository.VideoSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoSessionService {

    @Autowired
    private VideoSessionRepository videoSessionRepository;

    @Autowired
    private UserRepository userRepository;


    public List<VideoSession> getAllVideoSessionsByUserAndDate(Long userId, LocalDate date) {
        // 유저 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with ID: " + userId)
        );
        return videoSessionRepository.findVideoSessionsByUserAndDate(user, date);
    }
}

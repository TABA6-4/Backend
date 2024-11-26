package com.example.focus.service;


import com.example.focus.dto.ConcentrationSummaryDTO;
import com.example.focus.entity.ConcentrationResult;
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
public class VideoSessionService {

    @Autowired
    private final VideoSessionRepository videoSessionRepository;

    public VideoSessionService(VideoSessionRepository videoSessionRepository) {
        this.videoSessionRepository = videoSessionRepository;
    }

    @Autowired
    private UserRepository userRepository;


    public List<VideoSession> getAllVideoSessionsByUserAndDate(Long userId, LocalDate date) {
        // 유저 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with ID: " + userId)
        );
        return videoSessionRepository.findVideoSessionsByUserAndDate(user, date);
    }

    public ConcentrationSummaryDTO getConcentrationSummary(Long userId, LocalDate date) {
        List<VideoSession> sessions = videoSessionRepository.findAllByUserAndDateWithConcentrationResult(userId, date);

        long totalFocusedTime = 0;
        long totalNotFocusedTime = 0;
        long totalDuration = 0;

        for (VideoSession session : sessions) {
            ConcentrationResult result = session.getConcentrationResult();
            if (result != null) {
                totalFocusedTime += result.getFocusedTime().toSecondOfDay(); // Time to seconds
                totalNotFocusedTime += result.getNotFocusedTime().toSecondOfDay(); // Time to seconds
            }
            totalDuration += session.getDuration();
        }

        double focusedRatio = totalDuration > 0 ? (double) totalFocusedTime / totalDuration : 0.0;
        double notFocusedRatio = totalDuration > 0 ? (double) totalNotFocusedTime / totalDuration : 0.0;

        return new ConcentrationSummaryDTO(totalFocusedTime, totalNotFocusedTime, totalDuration, focusedRatio, notFocusedRatio);
    }

}

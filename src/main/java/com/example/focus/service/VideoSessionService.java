package com.example.focus.service;

import com.example.focus.dto.concentrationResult.ConcentrationSummaryDTO;
import com.example.focus.entity.ConcentrationResult;
import com.example.focus.entity.User;
import com.example.focus.entity.VideoSession;
import com.example.focus.repository.UserRepository;
import com.example.focus.repository.VideoSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
                totalFocusedTime += result.getFocusedTime().toSecondOfDay(); // 초 단위로 변환
                totalNotFocusedTime += result.getNotFocusedTime().toSecondOfDay(); // 초 단위로 변환
            }
            totalDuration += session.getDuration();
        }

        double focusedRatio = totalDuration > 0 ? (double) totalFocusedTime / totalDuration : 0.0;
        double notFocusedRatio = totalDuration > 0 ? (double) totalNotFocusedTime / totalDuration : 0.0;

        // 날짜와 사용자 ID를 포함하여 DTO 반환
        return new ConcentrationSummaryDTO(
                date,
                userId,
                totalFocusedTime,
                totalNotFocusedTime,
                totalDuration,
                focusedRatio,
                notFocusedRatio
        );
    }

    public List<ConcentrationSummaryDTO> getWeeklyConcentrationSummary(Long userId, LocalDate startDate) {
        List<ConcentrationSummaryDTO> summaries = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            ConcentrationSummaryDTO summary = getConcentrationSummary(userId, date);
            summaries.add(summary);
        }

        return summaries;
    }
}
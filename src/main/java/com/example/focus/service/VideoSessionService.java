package com.example.focus.service;

import com.example.focus.dto.concentrationResult.ConcentrationSummaryDTO;
import com.example.focus.dto.videoSession.VideoSessionDTO;
import com.example.focus.entity.ConcentrationResult;
import com.example.focus.entity.MessageData;
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

    /*public List<VideoSession> getAllVideoSessionsByUserAndDate(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found with ID: " + userId)
        );
        return videoSessionRepository.findVideoSessionsByUserAndDate(user, date);
    }*/

    public ConcentrationSummaryDTO getConcentrationSummary(Long userId, LocalDate date) {
        // UserId, 날짜를 받아서 해당 날짜의 집중도 리포트 출력
        // videoSession 리스트를 받아서 원소들의 멤버 변수를 각각 더하여 출력
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

    public List<VideoSessionDTO> getVideoSessionsWithConcentration(Long userId, LocalDate date) {
        //비디오 세션별 집중도 결과 출력
        List<VideoSession> sessions = videoSessionRepository.findAllByUserAndDateWithConcentrationResult(userId, date);
        List<VideoSessionDTO> sessionDTOs = new ArrayList<>();

        for (VideoSession session : sessions) {
            ConcentrationResult result = session.getConcentrationResult();

            long focusedTime = result != null ? result.getFocusedTime().toSecondOfDay() : 0; // 초 단위
            long notFocusedTime = result != null ? result.getNotFocusedTime().toSecondOfDay() : 0; // 초 단위
            long totalTime = focusedTime + notFocusedTime;

            double focusRatio = totalTime > 0 ? (double) focusedTime / totalTime : 0.0;
            double notFocusRatio = totalTime > 0 ? (double) notFocusedTime / totalTime : 0.0;

            sessionDTOs.add(new VideoSessionDTO(
                    session.getSession_id(),
                    session.getTitle(), // 세션 이름 예시
                    focusedTime,
                    notFocusedTime,
                    focusRatio,
                    notFocusRatio
            ));
        }

        return sessionDTOs;
    }


    //user_id와 title로 video session 조회
    public VideoSession createSession(MessageData messageData) {
        User user = userRepository.findById(messageData.getUser_id()).orElse(null);
        if(user == null){
            return null;
        }

        VideoSession videoSession = videoSessionRepository.findByUserAndTitle(user, messageData.getTitle());
        return videoSession;
    }
}
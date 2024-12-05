package com.example.focus.service;

import com.example.focus.dto.concentrationResult.ConcentrationSummaryDTO;
import com.example.focus.dto.videoSession.VideoSessionDTO;
import com.example.focus.dto.videoSession.VideoSessionRequestDTO;
import com.example.focus.dto.videoSession.VideoSessionResponseDTO;
import com.example.focus.entity.ConcentrationResult;
import com.example.focus.entity.User;
import com.example.focus.entity.VideoSession;
import com.example.focus.repository.UserRepository;
import com.example.focus.repository.VideoSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoSessionService {

    @Autowired
    private final VideoSessionRepository videoSessionRepository;

    @Autowired
    private UserRepository userRepository;

    public VideoSessionService(VideoSessionRepository videoSessionRepository) {
        this.videoSessionRepository = videoSessionRepository;
    }

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

    public VideoSessionResponseDTO startSession(VideoSessionRequestDTO request) {
        if (request.getUser_id() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUser_id()));
        VideoSession session = new VideoSession();
        session.setUser(user);
        session.setTitle(request.getTitle());
        session.setStartTime(LocalDateTime.now());
        session.setDate(Date.valueOf(LocalDateTime.now().toLocalDate()));

        VideoSession savedSession = videoSessionRepository.save(session);

        return new VideoSessionResponseDTO(
                savedSession.getSession_id(),
                savedSession.getUser().getUser_id(),
                savedSession.getTitle(),
                savedSession.getStartTime(),
                savedSession.getEndTime(),
                savedSession.getDuration()
        );
    }

    public VideoSessionResponseDTO endSession(Long sessionId) {
        // 세션 조회
        VideoSession session = videoSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + sessionId));

        // 종료 시간 설정
        LocalDateTime now = LocalDateTime.now();
        session.setEndTime(now);

        // 세션 길이 계산
        if (session.getStartTime() == null) {
            throw new IllegalStateException("Start time must be set before ending the session");
        }

        long duration = Duration.between(session.getStartTime(), now).getSeconds();
        session.setDuration(duration);

        // 업데이트된 세션 저장
        VideoSession updatedSession = videoSessionRepository.save(session);

        return new VideoSessionResponseDTO(
                updatedSession.getSession_id(),
                updatedSession.getUser().getUser_id(),
                updatedSession.getTitle(),
                updatedSession.getStartTime(),
                updatedSession.getEndTime(),
                updatedSession.getDuration()
        );
    }

    private VideoSessionResponseDTO mapToResponseDTO(VideoSession session) {
        return new VideoSessionResponseDTO(
                session.getSession_id(),
                session.getUser().getUser_id(),
                session.getTitle(),
                session.getStartTime(),
                session.getEndTime(),
                session.getDuration()
        );
    }
}
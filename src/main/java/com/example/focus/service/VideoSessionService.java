package com.example.focus.service;

import com.example.focus.dto.concentrationResult.ConcentrationSummaryDTO;
import com.example.focus.dto.videoSession.VideoSessionDTO;
import com.example.focus.dto.videoSession.VideoSessionRequestDTO;
import com.example.focus.dto.videoSession.VideoSessionResponseDTO;
import com.example.focus.entity.*;
import com.example.focus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoSessionService {

    @Autowired
    private final VideoSessionRepository videoSessionRepository;

    @Autowired
    PlannerService plannerService;

    public VideoSessionService(VideoSessionRepository videoSessionRepository) {
        this.videoSessionRepository = videoSessionRepository;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private ConcentrationResultRepository concentrationResultRepository;

    @Autowired
    private VideoFrameRepository videoFrameRepository;


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

                long tempFocusedTime = result.getFocusedTime().toSecondOfDay();
                long tempNotFocusedTime = result.getNotFocusedTime().toSecondOfDay();

                totalFocusedTime += tempFocusedTime; // 초 단위로 변환
                totalNotFocusedTime += tempNotFocusedTime; // 초 단위로 변환
                totalDuration += tempFocusedTime + tempNotFocusedTime;
            }

        }
        double focusedRatio = 0;
        double notFocusedRatio = 0;

        if(totalDuration > 0){
            focusedRatio = (double) totalFocusedTime / totalDuration;
            notFocusedRatio = (double) totalNotFocusedTime / totalDuration;
        }
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
    public VideoSession findSession(Long user_id, String title) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            System.err.println("User with ID " + user_id + " not found");
            return null;
        }

        return videoSessionRepository.findByUserAndTitle(user, title);
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
        session.setDate(LocalDateTime.now().toLocalDate());

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

        //플래너에 세션 저장
        plannerService.createSessionPlanner(session);

        calculateVideoFrame(videoFrameRepository.findAllVideoFrameBySessionId(sessionId));

        return new VideoSessionResponseDTO(
                updatedSession.getSession_id(),
                updatedSession.getUser().getUser_id(),
                updatedSession.getTitle(),
                updatedSession.getStartTime(),
                updatedSession.getEndTime(),
                updatedSession.getDuration()
        );
    }



    // 세션 종료 후 비디오 프레임을 분석해 집중도 계산
    private void calculateVideoFrame(List<VideoFrame> videoFrames) {
        if (videoFrames.isEmpty()) {
            throw new IllegalArgumentException("Video frames must not be empty");
        }
        // 밀리초 단위로 계산
        long focusedTimeMillis = 0;
        long notFocusedTimeMillis = 0;

        // 세션 ID 정보를 바탕으로 비디오세션 객체 생성
        VideoSession videoSession = videoSessionRepository.findById(videoFrames.get(0).getVideoSession().getSession_id())
                .orElseThrow(() -> new IllegalArgumentException("Video session not found"));

        // ConcentrationResult 테이블에 새롭게 추가할 정보를 담는 임시 객체(데이터) 생성
        ConcentrationResult result = new ConcentrationResult();
        result.setVideoSession(videoSession);

        // 밀리초 단위로 총 집중시간/비집중 시간 계산
        LocalDateTime previousTimestamp = videoFrames.get(0).getTimestamp();
        for (VideoFrame frame : videoFrames) {
            long durationMillis = Duration.between(previousTimestamp, frame.getTimestamp()).toMillis();
            if (frame.getConcentration() < 2) {
                focusedTimeMillis += durationMillis;
            } else {
                notFocusedTimeMillis += durationMillis;
            }
            previousTimestamp = frame.getTimestamp();
        }

        // DB에 저장되는 결과는 초 단위로 저장
        result.setFocusedTime(LocalTime.ofSecondOfDay(focusedTimeMillis/1000));
        result.setNotFocusedTime(LocalTime.ofSecondOfDay(notFocusedTimeMillis/1000));
        result.setAverageScore((double) focusedTimeMillis / (focusedTimeMillis + notFocusedTimeMillis) * 100);

        concentrationResultRepository.save(result);

        // 일일 리포트 업데이트하는 메소드 호출
        updateDailyReport(videoSession);
    }

    // 세션 종료 후 세션 정보를 바탕으로 일일 리포트 신규 작성 혹은 업데이트
    private void updateDailyReport(VideoSession videoSession) {
        long userId = videoSession.getUser().getUser_id();
        long focusedTime  = 0;
        long notFocusedTime = 0;

        LocalDate date = videoSession.getDate();

        //유저ID와 날짜(전달받은 파라미터에 포함) 기반하여 같은 날짜의 비디오세션 리스트 초기화
        List<VideoSession> sessions = videoSessionRepository.findByUserAndDate(userId, date);

        //비디오세션 리스트를 순회하면서 ConcentrationResult 값들을 참조하여 총 집중시간/비집중시간 계산
        for (VideoSession v : sessions) {
            ConcentrationResult tempResult = concentrationResultRepository.findBySessionId(v.getSession_id());
            focusedTime += tempResult.getFocusedTime().toSecondOfDay();
            notFocusedTime += tempResult.getNotFocusedTime().toSecondOfDay();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));


        // DailyReport 객체 조회
        DailyReport dailyReport = dailyReportRepository.findByUserIdAndDate(userId, videoSession.getDate());
        if (dailyReport == null) {
            // DailyReport가 없으면 새로 생성
            dailyReport = new DailyReport();
            dailyReport.setUser(user);
            dailyReport.setDate(videoSession.getDate());
        }

        //생성한 일일리포트 객체에 값 대입하고 저장
        double score = 0;
        if (focusedTime + notFocusedTime > 0) {
            score = (double) focusedTime / (focusedTime + notFocusedTime);
        }
        dailyReport.setTotalTime(focusedTime);
        dailyReport.setPercentage(score * 100);

        dailyReportRepository.save(dailyReport);
    }
    public VideoSessionResponseDTO getSession(Long sessionId) {
        VideoSession videoSession = videoSessionRepository.findById(sessionId).orElse(null);
        if (videoSession == null) {
            throw new IllegalArgumentException("Video session not found with ID: " + sessionId);
        }

        return new VideoSessionResponseDTO(
                videoSession.getSession_id(),
                videoSession.getUser().getUser_id(),
                videoSession.getTitle(),
                videoSession.getStartTime(),
                videoSession.getEndTime(),
                videoSession.getDuration()
        );



    }
}
package com.example.focus.controller;


import com.example.focus.dto.videoSession.VideoSessionDTO;
import com.example.focus.dto.videoSession.VideoSessionRequestDTO;
import com.example.focus.dto.videoSession.VideoSessionResponseDTO;
import com.example.focus.entity.VideoSession;
import com.example.focus.service.VideoSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/video-session")
@RequiredArgsConstructor
@Tag(name = "content", description = "영상 측정 관리 API") // API를 그룹화할 태그명을 지정
public class VideoSessionController {

    private final VideoSessionService videoSessionService;

   /* @GetMapping("/{userId}/{date}")
    public ResponseEntity<List<VideoSessionDTO>> getVideoSessions(
            @PathVariable Long userId, @PathVariable @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate date) {
        List<VideoSession> sessions = videoSessionService.getAllVideoSessionsByUserAndDate(userId, date);
        List<VideoSessionDTO> dtos = sessions.stream()
                .map(VideoSessionDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }*/

    @GetMapping("/{user_id}/{date}")
    @Operation(summary = "특정 날짜 content 집중도 조회", description = "userId와 date로 해당 날짜 content 집중도 조회")
    //UserId, 날짜 받아서 해당 날짜의 비디오 세션별 집중도 정리해서 출력
    public ResponseEntity<List<VideoSessionDTO>> getVideoSessionsByUserAndDate(
            @PathVariable Long user_id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        List<VideoSessionDTO> sessions = videoSessionService.getVideoSessionsWithConcentration(user_id, date);
        return ResponseEntity.ok(sessions);
    }

    // 세션 시작
    @PostMapping("/start")
    public ResponseEntity<VideoSessionResponseDTO> startSession(@Valid @RequestBody VideoSessionRequestDTO request) {
        VideoSessionResponseDTO response = videoSessionService.startSession(request);
        return ResponseEntity.ok(response);
    }

    // 세션 종료
    @PostMapping("/end/{sessionId}")
    public ResponseEntity<VideoSessionResponseDTO> endSession(@PathVariable Long sessionId) {
        VideoSessionResponseDTO response = videoSessionService.endSession(sessionId);
        return ResponseEntity.ok(response);
    }

    // 세션 종료
    @GetMapping("/{sessionId}")
    public ResponseEntity<VideoSessionResponseDTO> getSession(@PathVariable Long sessionId) {
        VideoSessionResponseDTO response = videoSessionService.getSession(sessionId);
        return ResponseEntity.ok(response);
    }
}

package com.example.focus.controller;


import com.example.focus.dto.videoSession.VideoSessionDTO;
import com.example.focus.entity.VideoSession;
import com.example.focus.service.VideoSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/video-session")
@RequiredArgsConstructor
public class VideoSessionController {

    private final VideoSessionService videoSessionService;

    @GetMapping("/{userId}/{date}")
    public ResponseEntity<List<VideoSessionDTO>> getVideoSessions(
            @PathVariable Long userId, @PathVariable @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate date) {
        List<VideoSession> sessions = videoSessionService.getAllVideoSessionsByUserAndDate(userId, date);
        List<VideoSessionDTO> dtos = sessions.stream()
                .map(VideoSessionDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
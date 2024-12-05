package com.example.focus.dto.videoSession;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VideoSessionResponseDTO {
    private Long sessionId;            // 세션 ID
    private Long userId;               // 유저 ID
    private String title;              // 세션 제목
    private LocalDateTime startTime;   // 시작 시간
    private LocalDateTime endTime;     // 종료 시간
    private long duration;             // 세션 길이 (초 단위)

}

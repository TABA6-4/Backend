package com.example.focus.dto.videoSession;

import com.example.focus.entity.VideoSession;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class VideoSessionDTO {
    private Long sessionId;            // 비디오 세션 ID
    private String sessionName;        // 비디오 세션 이름 (예시 필드)
    private long focusedTime;          // 집중 시간 (초 단위)
    private long notFocusedTime;       // 비집중 시간 (초 단위)
    private double focusRatio;         // 집중 비율 (0 ~ 1)
    private double notFocusRatio;      // 비집중 비율 (0 ~ 1)
}

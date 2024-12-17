package com.example.focus.dto.videoSession;

import com.example.focus.entity.VideoSession;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class VideoSessionDTO2 {
    private Long sessionId;            // 비디오 세션 ID
    private String sessionName;        // 비디오 세션 이름 (예시 필드)
    private long focused1;             // 집중 시간 (초 단위)
    private long focused2;
    private long notFocused1;          // 비집중 시간 (초 단위)
    private long notFocused2;
    private long sleepTime;
    private double focus1Ratio;         // 집중 비율 (0 ~ 1)
    private double focus2Ratio;
    private double notFocus1Ratio;      // 비집중 비율 (0 ~ 1)
    private double notFocus2Ratio;
    private double sleepRatio;
}

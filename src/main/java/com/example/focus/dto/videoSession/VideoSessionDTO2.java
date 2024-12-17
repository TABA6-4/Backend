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
    private long concentration0;
    private long concentration1;
    private long concentration2;
    private long concentration3;
    private long concentration4;
    private double concentrationRatio1;
    private double concentrationRatio2;
    private double concentrationRatio3;
    private double concentrationRatio4;
    private double concentrationRatio5;
}

package com.example.focus.dto.videoFrame;

import com.example.focus.entity.VideoFrame;
import com.example.focus.entity.VideoSession;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoFrameRequestDTO {

    @NotNull(message = "Session ID must not be null")
    private Long session_id;

    @NotNull(message = "Focus level must not be null")
    private int concentration;

    //@NotNull(message = "Timestamp must not be null")
    private LocalDateTime timestamp;

    // DTO -> Entity 변환 메서드
    public VideoFrame toEntity(VideoSession session) {
        VideoFrame videoFrame = new VideoFrame();
        videoFrame.setVideoSession(session);
        videoFrame.setConcentration(this.concentration);
        videoFrame.setTimestamp(LocalDateTime.now());
        return videoFrame;
    }
}

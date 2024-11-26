package com.example.focus.dto.videoSession;

import com.example.focus.entity.VideoSession;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class VideoSessionDTO {
    private Long session_id;
    private Long user_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private String title;
    private Date date;

    public VideoSessionDTO(VideoSession videoSession) {
        this.session_id = videoSession.getSession_id();
        this.user_id = videoSession.getUser().getUser_id();
        this.startTime = videoSession.getStartTime();
        this.endTime = videoSession.getEndTime();
        this.duration = videoSession.getDuration();
        this.title = videoSession.getTitle();
        this.date = videoSession.getDate();
    }
}

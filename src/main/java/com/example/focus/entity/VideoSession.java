package com.example.focus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "video_session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long session_id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration = 0L;
    private String title;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "videoSession", cascade = CascadeType.ALL)
    private List<Planner> planners;

    @OneToOne(mappedBy = "videoSession", cascade = CascadeType.ALL)
    private ConcentrationResult concentrationResult;

    @OneToMany(mappedBy = "videoSession", cascade = CascadeType.ALL)
    private List<VideoFrame> videoFrames;
}

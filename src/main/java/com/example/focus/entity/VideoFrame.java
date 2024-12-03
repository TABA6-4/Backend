package com.example.focus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_frame")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long frameId;

    private Double concentration;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private VideoSession videoSession;


}
